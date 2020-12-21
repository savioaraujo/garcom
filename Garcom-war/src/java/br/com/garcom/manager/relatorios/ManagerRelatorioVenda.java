/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.relatorios;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.DateUtils;
import br.com.foxinline.util.MessageUtils;
import br.com.foxinline.util.Utils;
import br.com.garcom.enums.FormaPagamento;
import br.com.garcom.enums.RelatorioVenda;
import br.com.garcom.manager.ManagerRelatorio;
import br.com.garcom.basico.modelo.Cliente;
import br.com.garcom.financeiro.modelo.ItemPagamento;
import br.com.garcom.estoque.modelo.Produto;
import br.com.garcom.venda.modelo.Venda;
import br.com.garcom.venda.relatorio.vo.ItemVendaVO;
import br.com.garcom.servico.ConfiguracoesServico;
import br.com.garcom.servico.VendaServico;
import br.com.garcom.usuario.modelo.Usuario;
import br.com.garcom.utils.RelatorioUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "VENDA", pageRedirect = "index.xhtml")
public class ManagerRelatorioVenda extends ManagerRelatorio {

    @EJB
    VendaServico vendaServico;
    @EJB
    ConfiguracoesServico configuracoesServico;
    private RelatorioVenda relatorioVenda;
    private Date dataInicial;
    private Date dataFinal;
    private Cliente cliente;
    private Produto produto;
    private List<Venda> vendas;
    private List<ItemVendaVO> itensVendasVo;
    private Map<String, Object> parametros;
    private Usuario usuarioLogado;

    @Override
    protected void init() {
        this.dataInicial = new Date();
        this.dataFinal = new Date();
        this.relatorioVenda = RelatorioVenda.VENDAS_POR_PERIODO;
        usuarioLogado = usuarioServico.getUserLogged();
        parametros = RelatorioUtils.obterCabecalhoFormatado(configuracoesServico.load(1l));
    }

    public void buscarDadosRelatorio() {
        vendas = null;
        itensVendasVo = null;
        if (relatorioVenda.equals(RelatorioVenda.VENDAS_POR_PERIODO)) {
            vendas = vendaServico.relatorioVendasPorPeriodo(dataInicial, dataFinal);
            String dadosEmissao = "Emitido por " + usuarioLogado.getNome() + " em " + DateUtils.format(new Date(), "dd/MM/yyyy HH:mm:ss");
            parametros.put("dadosEmissao", dadosEmissao);
            parametros.put("filtros", DateUtils.format(dataInicial) + " à " + DateUtils.format(dataFinal));

            BigDecimal totalVenda = BigDecimal.ZERO;
            BigDecimal totalEspecie = BigDecimal.ZERO;
            BigDecimal totalCredito = BigDecimal.ZERO;
            BigDecimal totalDebito = BigDecimal.ZERO;
            BigDecimal totalCarteiraDaLoja = BigDecimal.ZERO;
            BigDecimal totalUsoProprio = BigDecimal.ZERO;
            BigDecimal totalTaxas = BigDecimal.ZERO;
            calcularFormasPagamento(totalEspecie, totalCredito, totalDebito, totalCarteiraDaLoja, totalUsoProprio, totalVenda, totalTaxas);
        } else if (relatorioVenda.equals(RelatorioVenda.VENDAS_POR_CLIENTE)) {
            vendas = vendaServico.relatorioVendasPorCliente(dataInicial, dataFinal, cliente);
            String dadosEmissao = "Emitido por " + usuarioLogado.getNome() + " em " + DateUtils.format(new Date(), "dd/MM/yyyy HH:mm:ss");
            parametros.put("dadosEmissao", dadosEmissao);
            parametros.put("filtros", DateUtils.format(dataInicial) + " à " + DateUtils.format(dataFinal));

            BigDecimal totalVenda = BigDecimal.ZERO;
            BigDecimal totalEspecie = BigDecimal.ZERO;
            BigDecimal totalCredito = BigDecimal.ZERO;
            BigDecimal totalDebito = BigDecimal.ZERO;
            BigDecimal totalCarteiraDaLoja = BigDecimal.ZERO;
            BigDecimal totalUsoProprio = BigDecimal.ZERO;
            BigDecimal totalTaxas = BigDecimal.ZERO;
            calcularFormasPagamento(totalEspecie, totalCredito, totalDebito, totalCarteiraDaLoja, totalUsoProprio, totalVenda, totalTaxas);
        } else if (relatorioVenda.equals(RelatorioVenda.VENDAS_PRODUTO_MAIS_VENDIDO)) {
            itensVendasVo = vendaServico.relatorioVendasProdutosMaisVendidos(dataInicial, dataFinal);
            String dadosEmissao = "Emitido por " + usuarioLogado.getNome() + " em " + DateUtils.format(new Date(), "dd/MM/yyyy HH:mm:ss");
            parametros.put("dadosEmissao", dadosEmissao);
            parametros.put("filtros", DateUtils.format(dataInicial) + " à " + DateUtils.format(dataFinal));
        }


        if (!Utils.isEmpty(vendas) || !Utils.isEmpty(itensVendasVo)) {
            RequestContext.getCurrentInstance().execute("printButton.jq.click();");
        } else {
            MessageUtils.warn("Nenhum resultado encontrado.");
        }
    }

    private void calcularFormasPagamento(BigDecimal totalEspecie, BigDecimal totalCredito, BigDecimal totalDebito, BigDecimal totalCarteiraDaLoja, BigDecimal totalUsoProprio, BigDecimal totalVenda, BigDecimal totalTaxas) {
        for (Venda venda : vendas) {
            for (ItemPagamento itemPagamento : venda.getItensPagamento()) {
                if (itemPagamento.getFormaPagamento().equals(FormaPagamento.ESPECIE)) {
                    totalEspecie = totalEspecie.add(itemPagamento.getValor());
                } else if (itemPagamento.getFormaPagamento().equals(FormaPagamento.CARTAO_CREDITO)) {
                    totalCredito = totalCredito.add(itemPagamento.getValor());
                    if (itemPagamento.getTaxaJuros() != null) {
                        totalTaxas = totalTaxas.add(itemPagamento.getValor().multiply(itemPagamento.getTaxaJuros()));
                    }
                } else if (itemPagamento.getFormaPagamento().equals(FormaPagamento.CARTAO_DEBITO)) {
                    totalDebito = totalDebito.add(itemPagamento.getValor());
                    if (itemPagamento.getTaxaJuros() != null) {
                        totalTaxas = totalTaxas.add(itemPagamento.getValor().multiply(itemPagamento.getTaxaJuros()));
                    }
                } else if (itemPagamento.getFormaPagamento().equals(FormaPagamento.CREDITO_LOJA)) {
                    totalCarteiraDaLoja = totalCarteiraDaLoja.add(itemPagamento.getValor());
                } else if (itemPagamento.getFormaPagamento().equals(FormaPagamento.USO_INTERNO)) {
                    totalUsoProprio = totalUsoProprio.add(itemPagamento.getValor());
                }
            }
            totalVenda = totalVenda.add(venda.getValorTotal());
        }
        totalTaxas = totalTaxas.setScale(2, RoundingMode.HALF_EVEN);
        parametros.put("totalVendas", totalVenda);
        parametros.put("totalEspecie", totalEspecie);
        parametros.put("totalCredito", totalCredito);
        parametros.put("totalDebito", totalDebito);
        parametros.put("totalCarteiraLoja", totalCarteiraDaLoja);
        parametros.put("totalUsoProprio", totalUsoProprio);
        parametros.put("totalTaxaMaquina", totalTaxas);
        parametros.put("totalLiquido", totalVenda.subtract(totalTaxas));
    }

    public void gerarRelatorio() {
        if (!Utils.isEmpty(vendas)) {
            RelatorioUtils.gerarRelatorio(parametros, vendas, relatorioVenda.getUrl(), configuracoesServico.load(1l).getCaminhoRelatorio());
        } else if (!Utils.isEmpty(itensVendasVo)) {
            RelatorioUtils.gerarRelatorio(parametros, itensVendasVo, relatorioVenda.getUrl(), configuracoesServico.load(1l).getCaminhoRelatorio());
        }

    }

    public RelatorioVenda getRelatorioVenda() {
        return relatorioVenda;
    }

    public void setRelatorioVenda(RelatorioVenda relatorioVenda) {
        this.relatorioVenda = relatorioVenda;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
