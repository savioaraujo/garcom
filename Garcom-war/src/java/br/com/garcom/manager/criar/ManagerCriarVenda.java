/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.criar;

import br.com.garcom.basico.modelo.Cliente;
import br.com.garcom.financeiro.modelo.CarteiraLoja;
import br.com.garcom.financeiro.modelo.ItemMovimentacaoCarteiraLoja;
import br.com.garcom.financeiro.modelo.ItemPagamento;
import br.com.garcom.venda.modelo.ItemVenda;
import br.com.garcom.estoque.modelo.Produto;
import br.com.garcom.venda.modelo.Venda;
import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.DateUtils;
import br.com.foxinline.util.Utils;
import br.com.foxinline.util.bean.BeanUtils;
import br.com.garcom.basico.modelo.Bairro;
import br.com.garcom.basico.modelo.Endereco;
import br.com.garcom.enums.FormaPagamento;
import br.com.garcom.estoque.enums.TipoMovimentacaoEstoque;
import br.com.garcom.enums.venda.StatusVenda;
import br.com.garcom.enums.venda.TipoVenda;
import br.com.garcom.estoque.enums.UtilizacaoAditivoProduto;
import br.com.garcom.estoque.exceptions.EstoqueInsuficienteException;
import br.com.garcom.estoque.modelo.ItemAditivoProduto;
import br.com.garcom.financeiro.modelo.BandeiraCartao;
import br.com.garcom.financeiro.modelo.ItemJuros;
import br.com.garcom.financeiro.modelo.MaquinaCartao;
import br.com.garcom.helper.venda.VendaHelper;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerCriar;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.servico.*;
import br.com.garcom.usuario.modelo.Usuario;
import br.com.garcom.utils.CalendarUtils;
import br.com.garcom.utils.Caracteres;
import br.com.garcom.venda.modelo.ItemAditivoVenda;
import java.math.BigDecimal;
import java.util.*;
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
public class ManagerCriarVenda extends ManagerCriar<Venda> {

    @EJB
    VendaServico vendaServico;
    @EJB
    ItemVendaServico itemVendaServico;
    @EJB
    ClienteServico clienteServico;
    @EJB
    BairroServico bairroServico;
    @EJB
    CarteiraLojaServico carteiraLojaServico;
    @EJB
    ItemPagamentoServico itemPagamentoServico;
    @EJB
    TipoMovimentacaoCarteiraLojaServico tipoMovimentacaoCarteiraLojaCarteiraServico;
    @EJB
    ItemMovimentacaoEstoqueServico itemMovimentacaoEstoqueServico;
    @EJB
    MaquinaCartaoServico maquinaCartaoServico;
    @EJB
    BandeiraCartaoServico bandeiraCartaoServico;
    @EJB
    ItemJurosServico itemJurosServico;
    private ItemVenda itemVenda;
    private ItemVenda itemVendaRemocao;
    private ItemVenda itemVisualizacao;
    private Usuario usuarioLogado;
    private boolean adicionando;
    private Cliente clienteDialog;
    private ItemPagamento itemPagamento;
    private boolean pagar;
    private String tipoVenda = null;
    private List<MaquinaCartao> maquinasCartao;
    private ItemAditivoVenda itemAditivoVenda;

    @Override
    protected void init() {
        super.init();
        instanciarSubEntidades();
        pagar = Boolean.valueOf(obterParametro("pagar"));
    }

    @Override
    public void delegar() {
        super.delegar();
        if (!this.entidade.isAberta()) {
            Mensagem.messagemWarn("Está venda esta encerrada!");
            instanciar();
        }
    }

    @Override
    public void instanciar() {
        super.instanciar();

        this.entidade.setUsuarioAbertura(usuarioServico.getUserLogged());
        this.entidade.setDataAbertura(new Date());
        this.entidade.setValorTotal(BigDecimal.ZERO);
        this.entidade.setStatusVenda(StatusVenda.ABERTA);
        if (this.tipoVenda == null) {
            this.tipoVenda = obterParametro("tipo");
        }

        if (!Utils.isEmpty(tipoVenda) && this.entidade.getId() == null) {
            if (tipoVenda.equals("ORCAMENTO")) {
                this.entidade.setTipoVenda(TipoVenda.ORCAMENTO);
            } else {
                this.entidade.setTipoVenda(TipoVenda.VENDA);
            }
        }
    }

    private void instanciarSubEntidades() {
        this.itemVenda = new ItemVenda();
        this.usuarioLogado = usuarioServico.getUserLogged();
        this.clienteDialog = new Cliente();
        this.clienteDialog.setEndereco(new Endereco());
        this.itemPagamento = new ItemPagamento();
    }

    public List<Bairro> bairro(String query) {
        return bairroServico.autocompletar(query, this.clienteDialog.getEndereco().getCidade());
    }

    public void adicionarItem() {
        if (!adicionando) {
            this.adicionando = true;

            if (this.itemVenda.getProduto() == null) {
                this.adicionando = false;
                return;
            } else if (this.itemVenda.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                Mensagem.messagemWarn("Não é possível inserir um item com valor zero ou inferior.");
                this.adicionando = false;
                // Para feedback na tela quando inserido pelo ENTER, recalcula o total
                this.itemVenda.setValorTotal(BigDecimal.ZERO);
                return;
            }
            // Recalculo para casos de ter sido inserido pelo enter
            calcularTotalItem();

            if (this.itemVenda.getValorTotal().compareTo(BigDecimal.ZERO) <= 0) {
                Mensagem.messagemWarn("Não é possível inserir um item com valor total zero ou inferior.");
                this.adicionando = false;
                return;
            }


            this.itemVenda.setDataAdicionado(new Date());
            this.itemVenda.setUsuarioCadastro(usuarioLogado);
            if (this.entidade.getId() == null) {
                Venda vendaAberta = vendaServico.buscarVendaAbertaPorCliente(this.entidade.getCliente(), this.entidade.getTipoVenda());
                if (vendaAberta != null) {
                    this.entidade = vendaAberta;
                }
                salvarVenda();
            }

            this.itemVenda.setVenda(this.entidade);
            if (this.itemVenda.getProduto().getId() == null) {
                this.itemVenda.setProduto(null);
            }

            // Seta os aditivos no item
            if (this.itemAditivoVenda != null && this.itemAditivoVenda.getQuantidade() > 0) {
                this.itemVenda.getItensAditivoVenda().add(this.itemAditivoVenda);
            }

            if (this.entidade.getTipoVenda().equals(TipoVenda.VENDA)) {
                // efetua as movimentações de estoque
                try {
                    itemVendaServico.salvarEMovimentarEstoque(this.itemVenda);
                    this.itemVenda = new ItemVenda();
                } catch (EstoqueInsuficienteException ex) {
                    Mensagem.messagemError(ex.getMessage());
                    Logger.getLogger(ManagerCriarVenda.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Tenta dar a baixa no estoque do aditivo
                if (this.itemAditivoVenda != null && this.itemAditivoVenda.getQuantidade() > 0) {
                    try {
                        itemMovimentacaoEstoqueServico.movimentarEstoque(this.itemAditivoVenda.getProduto(), TipoMovimentacaoEstoque.VENDA, this.itemAditivoVenda.getQuantidade(), this.entidade);
                        this.itemAditivoVenda = null;
                    } catch (EstoqueInsuficienteException ex) {
                        Mensagem.messagemError(ex.getMessage());
                        Logger.getLogger(ManagerCriarVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    this.itemVenda = null;
                }
            } else {
                itemVendaServico.save(this.itemVenda);
            }

            this.entidade = vendaServico.load(this.entidade.getId());
            // Totaliza apos salvar para garantir os totais conforme a versão recem recuperada 
            totalizarVenda();

            vendaServico.update(this.entidade);
            this.adicionando = false;
            this.entidade = vendaServico.load(this.entidade.getId());
        }
    }

    private void totalizarVenda() {
        BigDecimal totalVenda = BigDecimal.ZERO;
        for (ItemVenda itemVenda : this.entidade.getItemVenda()) {
            if (itemVenda.getValorTotalComAditivo() == null) {
                totalVenda = totalVenda.add(itemVenda.getValorTotal());
            } else {
                totalVenda = totalVenda.add(itemVenda.getValorTotalComAditivo());
            }
        }

        this.entidade.setValorTotal(totalVenda);
    }

    public void removerItemVenda() {
        if (this.itemVendaRemocao != null) {
            this.entidade = vendaServico.load(this.entidade.getId());
            this.vendaServico.update(this.entidade);

            this.itemVendaRemocao.setActive(false);
            this.itemVendaRemocao.setVendaOrigemRemocao(this.entidade);
            this.itemVendaRemocao.setVenda(null);
            this.itemVendaRemocao.setDataRemocao(new Date());
            this.itemVendaRemocao.setUsuarioRemocao(usuarioLogado);
            if (this.entidade.getTipoVenda().equals(TipoVenda.VENDA)) {
                try {
                    this.itemVendaServico.inativarEMovimentarEstoque(this.itemVendaRemocao);
                    Mensagem.messagemInfo("Item removido com sucesso !");
                } catch (EstoqueInsuficienteException ex) {
                    Logger.getLogger(ManagerCriarVenda.class.getName()).log(Level.SEVERE, null, ex);
                    Mensagem.messagemInfo("Ocorreu um erro durante a remoçao do item !");
                }

                try {
                    for (ItemAditivoVenda itemAditivoVenda : this.itemVendaRemocao.getItensAditivoVenda()) {
                        this.itemMovimentacaoEstoqueServico.movimentarEstoque(itemAditivoVenda.getProduto(), TipoMovimentacaoEstoque.CANCELAMENTO_VENDA, itemAditivoVenda.getQuantidade(), this.entidade);
                    }
                } catch (EstoqueInsuficienteException ex) {
                    Logger.getLogger(ManagerCriarVenda.class.getName()).log(Level.SEVERE, null, ex);
                    Mensagem.messagemInfo("Ocorreu um erro durante a remoçao do item !");
                }
            } else {
                this.itemVendaServico.inactivate(this.itemVendaRemocao);
            }

            this.entidade = this.vendaServico.load(this.entidade);
            totalizarVenda();
            this.vendaServico.update(this.entidade);

            this.itemVendaRemocao = null;
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("confirmarRemocaoItemDialog.hide();");
        }

    }

    private void salvarVenda() {
        this.entidade.setAberta(true);
        this.entidade.setDataAbertura(new Date());
        this.entidade.setUsuarioAbertura(usuarioLogado);
        vendaServico.save(this.entidade);
    }

    public void buscarVendasCliente() {

        Venda vendaAberta = vendaServico.buscarVendaAbertaPorCliente(this.entidade.getCliente(), this.entidade.getTipoVenda());

        if (vendaAberta != null) {
            this.entidade = vendaAberta;
            Mensagem.messagemInfo("Localizado " + vendaAberta.getTipoVenda().getDescricao().toLowerCase() + " do cliente em aberto.");
        } else {
            // Caso a venda atual ja tenha um id e o cliente estava null
            // deve-se apenas atualizar a venda
            if (this.entidade.getId() != null) {
                this.vendaServico.update(this.entidade);
                Mensagem.messagemInfo("Venda atualizada!");
                return;
            } else {
                Cliente cliente = this.entidade.getCliente();
                instanciar();
                instanciarSubEntidades();
                this.entidade.setCliente(cliente);
            }
        }
    }

    public void selecionarProduto() {
        Produto produto = this.itemVenda.getProduto();
        if (produto != null && produto.getId() != null) {
            this.itemVenda.setValorUnitarioOriginal(produto.getValor());
            this.itemVenda.setDescricaoProduto(produto.getDescricao());
            this.itemVenda.setValorUnitario(produto.getValor());
            if (this.itemVenda.getValorUnitario() == null) {
                this.itemVenda.setValorUnitario(BigDecimal.ZERO);
                this.itemVenda.setValorUnitarioOriginal(BigDecimal.ZERO);
            }
            if (produto.getUtilizacaoAditivo() != null && produto.getUtilizacaoAditivo().equals(UtilizacaoAditivoProduto.UTILIZA_UM_ADITIVO)) {
                this.itemAditivoVenda = new ItemAditivoVenda();
                for (ItemAditivoProduto itemAditivoProduto : produto.getItensAditivoProduto()) {
                    final Produto produtoAditivo = itemAditivoProduto.getProduto();
                    this.itemAditivoVenda.setItemAditivoProduto(itemAditivoProduto);
                    this.itemAditivoVenda.setProduto(produtoAditivo);
                    this.itemAditivoVenda.setRetornavel(produtoAditivo.getUtilizacaoAditivo() != null && produtoAditivo.getUtilizacaoAditivo().equals(UtilizacaoAditivoProduto.EH_UM_ADITIVO_RETORNAVEL));
                    this.itemAditivoVenda.setValorUnitario(produtoAditivo.getValor());
                    if (this.itemAditivoVenda.isRetornavel()) {
                        this.itemAditivoVenda.setDataVencimento(CalendarUtils.proximoDiaUtilSemFeriado(new Date(), produtoAditivo.getPrazoDevolucao(), false));
                    }
                    // Chama a verificação do campo quantidade
                    onBlurQuantiade();
                    break;
                }
            } else {
                this.itemAditivoVenda = null;
            }
            calcularTotalItem();
        } else {
            if (produto != null) {
                this.itemVenda.setDescricaoProduto(itemVenda.getProduto().getDescricao());
                itemVenda.setProduto(null);
                if (itemVenda.getValorUnitarioOriginal() != null) {
                    this.itemVenda.setValorUnitarioOriginal(null);
                    this.itemVenda.setValorUnitario(BigDecimal.ZERO);
                    this.itemVenda.setValorTotal(BigDecimal.ZERO);
                }
                calcularTotalItem();
            }
        }
    }

    public void onBlurQuantiade() {
        if (this.itemVenda.getQuantidade() == null || this.itemVenda.getQuantidade() <= 0) {
            this.itemVenda.setQuantidade(1);
        }

        if (this.itemAditivoVenda != null) {
            this.itemAditivoVenda.setQuantidade(this.itemVenda.getQuantidade() * this.itemAditivoVenda.getItemAditivoProduto().getQuantidade());
        }
        calcularTotalItem();
    }

    public void onBlurValor() {

        if (this.itemVenda.getValorUnitario() == null) {
            this.itemVenda.setValorUnitario(BigDecimal.ZERO);
        }
        if (this.itemVenda.getProduto() != null) {
            if (this.itemVenda.getValorUnitario().compareTo(this.itemVenda.getProduto().getValorMinimo()) < 0) {
                this.itemVenda.setValorUnitario(this.itemVenda.getProduto().getValorMinimo());
                Mensagem.messagemWarn("O valor do produto nao pode ser menor que " + Caracteres.formataValor(this.itemVenda.getProduto().getValorMinimo().doubleValue(), false));
            } else if (this.itemVenda.getValorUnitario().compareTo(this.itemVenda.getProduto().getValor()) > 0) {
                this.itemVenda.setValorUnitario(this.itemVenda.getProduto().getValor());
                Mensagem.messagemWarn("Nao e possivel cobrar um valor maior que " + Caracteres.formataValor(this.itemVenda.getProduto().getValor().doubleValue(), false));
            }
        }
        calcularTotalItem();
    }

    public void calcularTotalItem() {
        this.itemVenda.setValorTotal(this.itemVenda.getValorUnitario().multiply(new BigDecimal(this.itemVenda.getQuantidade())));
        this.itemVenda.setValorTotalComAditivo(null);
        if (this.itemAditivoVenda != null) {
            this.itemAditivoVenda.setValorTotal(this.itemAditivoVenda.getValorUnitario().multiply(new BigDecimal(this.itemAditivoVenda.getQuantidade())));
            if (!this.itemAditivoVenda.isRetornavel()) {
                this.itemVenda.setValorTotalComAditivo(this.itemVenda.getValorTotal().add(this.itemAditivoVenda.getValorTotal()));
            }
        }
    }

    public void setarCliente() {
        if (this.entidade.getCliente() != null && this.entidade.getCliente().getId() != null) {
            this.clienteDialog = this.entidade.getCliente();
        } else {
            this.clienteDialog = new Cliente();
            this.clienteDialog.setEndereco(new Endereco());
        }
    }

    public void salvarCliente() {
        if (this.clienteDialog.getId() == null) {
            clienteServico.save(this.clienteDialog);
        } else {
            clienteServico.update(clienteDialog);
        }
        this.entidade.setCliente(clienteDialog);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("clienteDialog.hide();");
    }

    public void adicionarItemPagamento() {
        if (!adicionando) {
            this.adicionando = true;
            if (itemPagamento.getValor().compareTo(BigDecimal.ZERO) > 0) {

                if (this.entidade.totalParcelas().add(itemPagamento.getValor()).compareTo(this.entidade.getValorTotal()) > 0) {
                    Mensagem.messagemWarn("O soma das parcelas será superior ao total da venda, verifique o total de pagamento registrados!");
                } else {

                    if (this.itemPagamento.getFormaPagamento().equals(FormaPagamento.CARTAO_CREDITO)
                            || this.itemPagamento.getFormaPagamento().equals(FormaPagamento.CARTAO_DEBITO)) {
                        if (this.itemPagamento.getItemJuros() == null) {
                        } else {
                            this.itemPagamento.setParcelamento(this.itemPagamento.getItemJuros().getParcelamento());
                            this.itemPagamento.setTaxaJuros(this.itemPagamento.getItemJuros().getTaxa());
                        }
                    }
                    this.entidade.getItensPagamento().add(this.itemPagamento);
                    this.itemPagamento = new ItemPagamento();
                }

            } else {
                Mensagem.messagemWarn("A parcela tem que ter valor maior que zero!");
            }
            this.adicionando = false;
        }
    }

    public void calculaPrazo() {
        if (this.itemPagamento.getFormaPagamento().equals(FormaPagamento.CREDITO_LOJA)) {

            this.itemPagamento.setPrazoPagamento(DateUtils.alterDate(new Date(), 30));
        } else {
            this.itemPagamento.setPrazoPagamento(null);
        }
    }

    public void removerParcela(ItemPagamento item) {
        this.entidade.getItensPagamento().remove(item);
    }

    public void removerParcelas() {
        this.entidade.setItensPagamento(null);
        this.itemPagamento = new ItemPagamento();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("fecharVendaDialog.hide();");
    }

    public boolean desabilitaBotaoVenda() {
        setarValorRestante();
        return this.entidade.getValorTotal().compareTo(BigDecimal.ZERO) <= 0;
    }

    public void fecharVenda() {
        if (!Utils.isEmpty(this.entidade.getItemVenda())) {
            if (Utils.isEmpty(this.entidade.getItensPagamento())) {
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.execute("fecharVendaDialog.hide();");
                requestContext.execute("confirmarFechamentoVenda.show();");
            } else if (entidade.getCliente() != null) {
                confirmarFechamentoVenda();
            } else {
                Mensagem.messagemWarn("É necessário informar o pagamento!");
            }
        } else {
            Mensagem.messagemWarn("É necessário adicionar no mínimo um item a venda!");
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("fecharVendaDialog.hide();");
        }

    }

    public void confirmarFechamentoVenda() {
        List<ItemPagamento> pagamentos = this.entidade.getItensPagamento();
        this.entidade = vendaServico.load(this.entidade.getId());

        this.entidade.setAberta(false);
        this.entidade.setDataFechamento(new Date());
        this.entidade.setUsuarioFechamento(usuarioLogado);
        this.entidade.setItensPagamento(pagamentos);
        this.entidade.setStatusVenda(StatusVenda.PAGO);
        this.vendaServico.update(this.entidade);

        List<ItemPagamento> itemPagamentoCarteira = new ArrayList<ItemPagamento>();
        for (ItemPagamento itemPagamento : entidade.getItensPagamento()) {
            itemPagamento.setVenda(this.entidade);
            itemPagamentoServico.update(itemPagamento);
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

        Mensagem.messagemInfoRedirect("Venda fechada com sucesso!", "index.xhtml");
    }

    public void setarValorRestante() {

        if (itemPagamento != null) {
            this.itemPagamento.setValor(this.entidade.getValorTotal().subtract(this.entidade.totalParcelas()));
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

    public void reiniciarVenda() {
        Mensagem.redirect("frenteCaixa.xhtml");
    }

    public List<ItemVenda> itensOrdenados() {
        if (this.entidade != null) {

            Collections.sort(this.entidade.getItemVenda(), new Comparator<ItemVenda>() {
                @Override
                public int compare(ItemVenda o1, ItemVenda o2) {
                    final int compareTo = o1.getDataAdicionado().compareTo(o2.getDataAdicionado());
                    return compareTo != 0 ? compareTo * -1 : 0;
                }
            });

            return this.entidade.getItemVenda();
        } else {
            return null;
        }
    }

    public void cancelarVenda() {
        VendaHelper.cancelarVenda(this.entidade, this.usuarioLogado, this.vendaServico, this.itemMovimentacaoEstoqueServico, this.itemPagamentoServico);
    }

    public void converterOrcamentoEmVenda() {


        this.entidade = vendaServico.load(this.entidade.getId());
        this.entidade = vendaServico.update(this.entidade);
        Date dataAtual = new Date();

        this.entidade.setAberta(false);
        this.entidade.setDataFechamento(dataAtual);
        this.entidade.setUsuarioFechamento(usuarioLogado);
        this.entidade.setStatusVenda(StatusVenda.CONVERTIDO);
        this.vendaServico.update(this.entidade);


        Venda venda = (Venda) BeanUtils.clone(this.entidade);
        venda.setTipoVenda(TipoVenda.VENDA);
        venda.setStatusVenda(StatusVenda.ABERTA);
        venda.setUsuarioAbertura(usuarioLogado);
        venda.setDataAbertura(dataAtual);
        venda.setOrcamentoOrigem(this.entidade);
        venda.setDataFechamento(null);
        venda.setUsuarioFechamento(null);
        venda.setAberta(true);
        venda.setItemVenda(null);
        venda.setId(null);
        vendaServico.save(venda);
        ItemVenda itemVendaNova = null;
        List<String> erros = new ArrayList<String>();

        for (ItemVenda itemVendaOrcamento : this.entidade.getItemVenda()) {
            try {
                itemVendaNova = (ItemVenda) BeanUtils.clone(itemVendaOrcamento);
                itemVendaNova.setId(null);
                itemVendaNova.setVenda(venda);
                itemVendaNova.setDataAdicionado(dataAtual);
                for (ItemAditivoVenda itemAditivoVendaNova : itemVendaNova.getItensAditivoVenda()) {
                    itemAditivoVendaNova.setId(null);
                }
                itemVendaServico.salvarEMovimentarEstoque(itemVendaNova);
            } catch (EstoqueInsuficienteException ex) {
                Logger.getLogger(ManagerCriarVenda.class.getName()).log(Level.SEVERE, null, ex);
                erros.add(ex.getMessage());
            }
            // Só tenta movimentar se salvou o item venda
            if (itemVendaNova != null && itemVendaNova.getId() != null) {
                try {
                    for (ItemAditivoVenda itemAditivoVendaOrcamento : itemVendaNova.getItensAditivoVenda()) {
                        this.itemMovimentacaoEstoqueServico.movimentarEstoque(itemAditivoVendaOrcamento.getProduto(), TipoMovimentacaoEstoque.VENDA, itemAditivoVendaOrcamento.getQuantidade(), venda);
                    }
                } catch (EstoqueInsuficienteException ex) {
                    Logger.getLogger(ManagerCriarVenda.class.getName()).log(Level.SEVERE, null, ex);
                    erros.add(ex.getMessage());
                }
            }
        }

        this.entidade = vendaServico.load(venda);
        totalizarVenda();
        vendaServico.update(this.entidade);
        this.entidade = vendaServico.load(this.entidade.getId());
        if (!Utils.isEmpty(erros)) {
            String mensagem = "Venda registrada, porem os seguintes itens não constam com a quantidade requerida : \n";
            for (String string : erros) {
                mensagem += string + "\n";
            }
            Mensagem.messagemInfoRedirect(mensagem, "frenteCaixa.xhtml?id=" + this.entidade.getId());
        } else {
            String mensagem = "Orçamento convertido com sucesso com sucesso !";
            Mensagem.messagemInfoRedirect(mensagem, "frenteCaixa.xhtml?id=" + this.entidade.getId());
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

    public ItemVenda getItemVenda() {
        return itemVenda;
    }

    public void setItemVenda(ItemVenda itemVenda) {
        this.itemVenda = itemVenda;
    }

    public ItemVenda getItemVendaRemocao() {
        return itemVendaRemocao;
    }

    public void setItemVendaRemocao(ItemVenda itemVendaRemocao) {
        this.itemVendaRemocao = itemVendaRemocao;
    }

    public ItemVenda getItemVisualizacao() {
        return itemVisualizacao;
    }

    public void setItemVisualizacao(ItemVenda itemVisualizacao) {
        this.itemVisualizacao = itemVisualizacao;
    }

    public Cliente getClienteDialog() {
        return clienteDialog;
    }

    public void setClienteDialog(Cliente clienteDialog) {
        this.clienteDialog = clienteDialog;
    }

    public ItemPagamento getItemPagamento() {
        return itemPagamento;
    }

    public void setItemPagamento(ItemPagamento itemPagamento) {
        this.itemPagamento = itemPagamento;
    }

    public boolean isPagar() {
        return pagar;
    }

    public void setPagar(boolean pagar) {
        this.pagar = pagar;
    }

    public ItemAditivoVenda getItemAditivoVenda() {
        return itemAditivoVenda;
    }

    public void setItemAditivoVenda(ItemAditivoVenda itemAditivoVenda) {
        this.itemAditivoVenda = itemAditivoVenda;
    }

    @Override
    protected ServicoGenerico getServico() {
        return vendaServico;
    }
}
