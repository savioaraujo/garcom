/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.Utils;
import br.com.garcom.enums.FormaPagamento;
import br.com.garcom.enums.venda.StatusVenda;
import br.com.garcom.enums.venda.TipoVenda;
import br.com.garcom.estoque.enums.TipoMovimentacaoEstoque;
import br.com.garcom.estoque.exceptions.EstoqueInsuficienteException;
import br.com.garcom.estoque.modelo.ItemMovimentacaoEstoque;
import br.com.garcom.financeiro.modelo.BandeiraCartao;
import br.com.garcom.financeiro.modelo.CarteiraLoja;
import br.com.garcom.financeiro.modelo.ItemJuros;
import br.com.garcom.financeiro.modelo.ItemMovimentacaoCarteiraLoja;
import br.com.garcom.financeiro.modelo.ItemPagamento;
import br.com.garcom.financeiro.modelo.MaquinaCartao;
import br.com.garcom.helper.venda.VendaHelper;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerPesquisar;
import br.com.garcom.manager.criar.ManagerCriarVenda;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.servico.BandeiraCartaoServico;
import br.com.garcom.servico.CarteiraLojaServico;
import br.com.garcom.servico.ItemAditivoVendaServico;
import br.com.garcom.servico.ItemJurosServico;
import br.com.garcom.servico.ItemMovimentacaoEstoqueServico;
import br.com.garcom.servico.ItemPagamentoServico;
import br.com.garcom.servico.MaquinaCartaoServico;
import br.com.garcom.servico.TipoMovimentacaoCarteiraLojaServico;
import br.com.garcom.venda.modelo.Venda;
import br.com.garcom.servico.VendaServico;
import br.com.garcom.usuario.modelo.Usuario;
import br.com.garcom.venda.modelo.ItemAditivoVenda;
import br.com.garcom.venda.modelo.ItemVenda;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "VENDA", pageRedirect = "index.xhtml")
public class ManagerPesquisarVenda extends ManagerPesquisar<Venda> {

    @EJB
    private VendaServico vendaServico;
    @EJB
    private ItemMovimentacaoEstoqueServico itemMovimentacaoEstoqueServico;
    @EJB
    private ItemAditivoVendaServico itemAditivoVendaServico;
    @EJB
    MaquinaCartaoServico maquinaCartaoServico;
    @EJB
    BandeiraCartaoServico bandeiraCartaoServico;
    @EJB
    ItemJurosServico itemJurosServico;
    @EJB
    CarteiraLojaServico carteiraLojaServico;
    @EJB
    ItemPagamentoServico itemPagamentoServico;
    @EJB
    TipoMovimentacaoCarteiraLojaServico tipoMovimentacaoCarteiraLojaCarteiraServico;
    private Date dataInicial;
    private Date dataFinal;
    private List<ItemAditivoVenda> itensPendentesDevolucao;
    private BigDecimal totalItensDevolucao;
    private ItemPagamento itemPagamento;
    private List<MaquinaCartao> maquinasCartao;
    private boolean adicionando;
    private List<ItemPagamento> itensPagamentos;
    private Usuario usuarioLogado;

    @Override
    public void pesquisar() {
        this.resultados = this.vendaServico.pesquisar(entidade, dataInicial, dataFinal);
    }

    @Override
    public void delegar() {
        super.delegar();
        this.usuarioLogado = usuarioServico.getUserLogged();
        this.itensPendentesDevolucao = new ArrayList<>();
        this.totalItensDevolucao = BigDecimal.ZERO;
        this.itemPagamento = new ItemPagamento();
        this.itensPagamentos = new ArrayList<>();
        for (ItemVenda itemVenda : this.entidade.getItemVenda()) {
            for (ItemAditivoVenda itemAditivoVenda : itemVenda.getItensAditivoVenda()) {
                if (itemAditivoVenda.isRetornavel() && itemAditivoVenda.getDataRecebimento() == null) {
                    itemAditivoVenda.setQuantidadeDevolvida(itemAditivoVenda.getQuantidade());
                    itemAditivoVenda.setValorASerPago(BigDecimal.ZERO);
                    this.itensPendentesDevolucao.add(itemAditivoVenda);
                }
            }
        }
    }

    public void calcularValorDebito() {
        this.totalItensDevolucao = BigDecimal.ZERO;
        for (ItemAditivoVenda itemAditivoVenda : this.itensPendentesDevolucao) {
            int quantidadeNaoDevolvida = itemAditivoVenda.getQuantidade() - itemAditivoVenda.getQuantidadeDevolvida();
            itemAditivoVenda.setValorASerPago(itemAditivoVenda.getValorUnitario().multiply(new BigDecimal(quantidadeNaoDevolvida)));
            this.totalItensDevolucao = this.totalItensDevolucao.add(itemAditivoVenda.getValorASerPago());
        }
    }

    public void confirmarRecebimento() {
        final Date dataAtual = new Date();
        for (ItemAditivoVenda itemAditivoVenda : this.itensPendentesDevolucao) {
            try {
                ItemMovimentacaoEstoque itemMovimentacao = this.itemMovimentacaoEstoqueServico.movimentarEstoque(itemAditivoVenda.getProduto(), TipoMovimentacaoEstoque.ENTRADA, itemAditivoVenda.getQuantidadeDevolvida(), this.entidade);
                itemMovimentacao.setDescricao("Devolvido pelo cliente.");
                itemMovimentacaoEstoqueServico.update(itemMovimentacao);
            } catch (EstoqueInsuficienteException ex) {
                Logger.getLogger(ManagerPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
            }
            itemAditivoVenda.setUsuarioRecebimento(usuarioLogado);
            itemAditivoVenda.setDataRecebimento(dataAtual);
            this.itemAditivoVendaServico.update(itemAditivoVenda);
        }
        salvarPagamentoParcelas();
    }

    public void salvarPagamentoParcelas() {
        List<ItemPagamento> itemPagamentoCarteira = new ArrayList<ItemPagamento>();
        for (ItemPagamento itemPagamento : this.itensPagamentos) {
            itemPagamento.setVenda(this.entidade);
            itemPagamento.setDescricao("Pagamento de multa no ato da devolução dos itens");
            itemPagamentoServico.save(itemPagamento);
            if (itemPagamento.getFormaPagamento().equals(FormaPagamento.CREDITO_LOJA)) {
                itemPagamentoCarteira.add(itemPagamento);
            }
        }

        // Se existir itens pagos com carteira da loja faz a movimentação
        if (!Utils.isEmpty(itemPagamentoCarteira)) {
            List<CarteiraLoja> result = carteiraLojaServico.buscarCarteiraPorCliente(entidade.getCliente());
            CarteiraLoja carteiraCliente = null;
            if (!Utils.isEmpty(result)) {
                carteiraCliente = result.get(0);
            } else {
                carteiraCliente = new CarteiraLoja();
                carteiraCliente.setActive(true);
                carteiraCliente.setDataCadastro(new Date());
                carteiraCliente.setProprietario(entidade.getCliente());
                carteiraCliente.setSaldo(BigDecimal.ZERO);
                carteiraCliente.setUsuarioCadastro(usuarioLogado);
                carteiraLojaServico.save(carteiraCliente);
                carteiraCliente = carteiraLojaServico.load(carteiraCliente.getId());
            }

            for (ItemPagamento itemPagamento : itemPagamentoCarteira) {
                ItemMovimentacaoCarteiraLoja itemMovimentacaoCarteiraLoja = new ItemMovimentacaoCarteiraLoja();
                itemMovimentacaoCarteiraLoja.setUsuarioCadastro(usuarioLogado);
                itemMovimentacaoCarteiraLoja.setVenda(entidade);
                itemMovimentacaoCarteiraLoja.setCarteiraLoja(carteiraCliente);
                itemMovimentacaoCarteiraLoja.setValor(itemPagamento.getValor());
                itemMovimentacaoCarteiraLoja.setMotivo("Pagamento de venda");
                itemMovimentacaoCarteiraLoja.setTipoMovimentacaoCarteiraLoja(tipoMovimentacaoCarteiraLojaCarteiraServico.obterMovimentacaoVenda());
                carteiraCliente.getItensMovimentacaoCarteiraLoja().add(itemMovimentacaoCarteiraLoja);
                carteiraCliente.setSaldo(carteiraCliente.getSaldo().subtract(itemMovimentacaoCarteiraLoja.getValor()));
                if (carteiraCliente.getDataUltimaMovimentacaoNegativa() == null && carteiraCliente.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
                    carteiraCliente.setDataUltimaMovimentacaoNegativa(new Date());
                }
            }
            carteiraLojaServico.update(carteiraCliente);
        }

        Mensagem.messagemInfoRedirect("Itens recebidos com sucesso !", "visualizarVenda.xhtml?id=" + this.entidade.getId());
    }

    // PAGAMENTO
    public void setarValorRestante() {

        if (itemPagamento != null) {
            this.itemPagamento.setValor(this.totalItensDevolucao.subtract(totalParcelas()));
            if (itemPagamento.getFormaPagamento().equals(FormaPagamento.CARTAO_CREDITO) || itemPagamento.getFormaPagamento().equals(FormaPagamento.CARTAO_DEBITO)) {
                maquinasDisponiveis("");
                if (!Utils.isEmpty(this.maquinasCartao)) {
                    this.itemPagamento.setMaquinaCartao(maquinasCartao.get(0));
                }
                bandeiraDisponiveis();
                parcelamentoDisponiveis();
            } else {
                itemPagamento.setMaquinaCartao(null);
                itemPagamento.setBandeiraCartao(null);
                itemPagamento.setParcelamento(null);
                itemPagamento.setTaxaJuros(null);
            }
        }
    }

    public List<MaquinaCartao> maquinasDisponiveis(String query) {
        MaquinaCartao maquina = new MaquinaCartao();
        maquina.setNome(query);
        if (itemPagamento.getFormaPagamento().equals(FormaPagamento.CARTAO_CREDITO)) {
            this.maquinasCartao = maquinaCartaoServico.pesquisar(maquina, null, true);
        } else {
            this.maquinasCartao = maquinaCartaoServico.pesquisar(maquina, true, null);
        }
        return this.maquinasCartao;
    }

    public List<SelectItem> bandeiraDisponiveis() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        if (this.itemPagamento.getMaquinaCartao() != null) {
            List<BandeiraCartao> bandeiras = bandeiraCartaoServico.obterBandeirasDisponiveisMaquina(this.itemPagamento.getMaquinaCartao(), this.itemPagamento.getFormaPagamento());
            if (!Utils.isEmpty(bandeiras)) {

                for (BandeiraCartao bandeira : bandeiras) {
                    items.add(new SelectItem(bandeira, bandeira.getNome()));
                }
                itemPagamento.setBandeiraCartao(bandeiras.get(0));
            }
        }
        return items;
    }

    public List<ItemJuros> parcelamentoDisponiveis() {
        if (this.itemPagamento.getMaquinaCartao() != null && this.itemPagamento.getBandeiraCartao() != null) {
            List<ItemJuros> itemsJuros = itemJurosServico.obterParcelamentoDisponiveis(this.itemPagamento.getMaquinaCartao(), this.itemPagamento.getBandeiraCartao());
            if (!Utils.isEmpty(itemsJuros)) {
                itemPagamento.setItemJuros(itemsJuros.get(0));
                return itemsJuros;
            }

        }
        return new ArrayList<ItemJuros>();
    }

    public void adicionarItemPagamento() {
        if (!adicionando) {
            this.adicionando = true;
            if (itemPagamento.getValor().compareTo(BigDecimal.ZERO) > 0) {

                if (totalParcelas().add(itemPagamento.getValor()).compareTo(this.totalItensDevolucao) > 0) {
                    Mensagem.messagemWarn("O soma das parcelas será superior ao total a ser pago, verifique o total de pagamento registrados!");
                } else {

                    if (this.itemPagamento.getFormaPagamento().equals(FormaPagamento.CARTAO_CREDITO)
                            || this.itemPagamento.getFormaPagamento().equals(FormaPagamento.CARTAO_DEBITO)) {
                        if (this.itemPagamento.getItemJuros() == null) {
                        } else {
                            this.itemPagamento.setParcelamento(this.itemPagamento.getItemJuros().getParcelamento());
                            this.itemPagamento.setTaxaJuros(this.itemPagamento.getItemJuros().getTaxa());
                        }
                    }
                    this.itensPagamentos.add(this.itemPagamento);
                    this.itemPagamento = new ItemPagamento();
                }

            } else {
                Mensagem.messagemWarn("A parcela tem que ter valor maior que zero!");
            }
            this.adicionando = false;
        }
    }

    public void removerParcela(ItemPagamento item) {
        this.itensPagamentos.remove(item);
    }

    public void removerParcelas() {
        this.itensPagamentos = new ArrayList<>();
        this.itemPagamento = new ItemPagamento();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("pagamentoDialog.hide();");
    }

    public BigDecimal totalParcelas() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPagamento itemPagamento : this.itensPagamentos) {
            total = total.add(itemPagamento.getValor());
        }
        return total;
    }
    // FIM PAGAMENTO

    public void cancelarVenda() {
        VendaHelper.cancelarVenda(this.entidade, this.usuarioLogado, this.vendaServico, this.itemMovimentacaoEstoqueServico, this.itemPagamentoServico);
    }

    public List<ItemPagamento> getItensPagamentos() {
        return itensPagamentos;
    }

    public void setItensPagamentos(List<ItemPagamento> itensPagamentos) {
        this.itensPagamentos = itensPagamentos;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public List<ItemAditivoVenda> getItensPendentesDevolucao() {
        return itensPendentesDevolucao;
    }

    public void setItensPendentesDevolucao(List<ItemAditivoVenda> itensPendentesDevolucao) {
        this.itensPendentesDevolucao = itensPendentesDevolucao;
    }

    public BigDecimal getTotalItensDevolucao() {
        return totalItensDevolucao;
    }

    public void setTotalItensDevolucao(BigDecimal totalItensDevolucao) {
        this.totalItensDevolucao = totalItensDevolucao;
    }

    public ItemPagamento getItemPagamento() {
        return itemPagamento;
    }

    public void setItemPagamento(ItemPagamento itemPagamento) {
        this.itemPagamento = itemPagamento;
    }

    @Override
    protected ServicoGenerico getServico() {
        return vendaServico;
    }
}
