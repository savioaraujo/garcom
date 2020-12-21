/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.enums;

/**
 *
 * @author evaldosavio
 */
public enum TipoMovimentacaoCarteira {

    CREDITO("Crédito"), DEBITO("Débito"), VENDA("Venda");
    private String nome;

    private TipoMovimentacaoCarteira(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String str() {
        return this.nome;
    }
}
