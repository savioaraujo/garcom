/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.enums;

/**
 *
 * @author evaldosavio
 */
public enum TipoCliente {

    PESSOA_FISICA("Pessoa Fisica"), PESSOA_JURIDICA("Pessoa Juridica");
    private String descricao;

    private TipoCliente(String nome) {
        this.descricao = nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
