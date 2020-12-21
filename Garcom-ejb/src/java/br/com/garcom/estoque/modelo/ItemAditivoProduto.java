/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.estoque.modelo;

import br.com.foxinline.annotation.NotAudited;
import br.com.foxinline.model.BasicBean;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "item_aditivo_produto")
public class ItemAditivoProduto extends BasicBean {

    @NotAudited
    @ManyToOne
    private Produto produto;
    private Integer quantidade;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
