/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.enums;

/**
 *
 * @author evaldosavio
 */
public enum FormaPagamento {

    ESPECIE("Espécie"),
    CARTAO_CREDITO("Cartão de crédito"),
    CARTAO_DEBITO("Cartão de débito"),
    CREDITO_LOJA("Credito da Loja"),
    USO_INTERNO("Uso próprio");
    private String nome;

    private FormaPagamento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
    
    public String str() {
        return this.nome;
    }
}
