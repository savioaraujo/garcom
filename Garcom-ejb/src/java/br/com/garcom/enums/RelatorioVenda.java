/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.enums;

/**
 *
 * @author evaldosavio
 */
public enum RelatorioVenda {

    VENDAS_POR_PERIODO("Vendas por período", "venda_por_periodo"),
    VENDAS_POR_CLIENTE("Vendas por cliente", "venda_por_cliente"),
    VENDAS_PRODUTO_MAIS_VENDIDO("Produtos mais vendidos", "produtos_mais_vendidos");
    private String nome;
    private String url;

    private RelatorioVenda(String nome) {
        this.nome = nome;
    }

    private RelatorioVenda(String nome, String url) {
        this.nome = nome;
        this.url = "br/com/garcom/venda/relatorio/" + url + ".jasper";
    }

    public String getNome() {
        return nome;
    }

    public String getUrl() {
        return url;
    }
}
