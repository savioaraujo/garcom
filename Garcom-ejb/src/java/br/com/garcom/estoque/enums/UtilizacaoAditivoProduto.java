/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.estoque.enums;

/**
 *
 * @author evaldosavio
 */
public enum UtilizacaoAditivoProduto {

    EH_UM_ADITIVO("É um aditivo"),
    EH_UM_ADITIVO_RETORNAVEL("É um aditivo retornável"),
    UTILIZA_UM_ADITIVO("Utiliza um aditivo");
    private String descricao;

    private UtilizacaoAditivoProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
