/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.venda.relatorio.vo;

import br.com.garcom.estoque.modelo.Produto;
import java.math.BigDecimal;

/**
 *
 * @author evaldosavio
 */
public class ItemVendaVO {

    private Produto produto;
    private Long quantidade;
    private BigDecimal total;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
