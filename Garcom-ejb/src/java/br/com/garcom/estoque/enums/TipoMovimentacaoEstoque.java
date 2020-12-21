/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.estoque.enums;

/**
 *
 * @author evaldosavio
 */
public enum TipoMovimentacaoEstoque {

    ENTRADA("Entrada"), SAIDA("Saída"), VENDA("Venda"), CANCELAMENTO_VENDA("Cancelamento de venda");
    private String nome;

    private TipoMovimentacaoEstoque(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String str() {
        return this.nome;
    }
}
