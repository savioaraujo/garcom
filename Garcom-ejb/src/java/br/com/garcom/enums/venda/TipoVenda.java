/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.enums.venda;

/**
 *
 * @author evaldosavio
 */
public enum TipoVenda {

    VENDA("Venda"), ORCAMENTO("Orçamento");
    private String descricao;

    private TipoVenda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
