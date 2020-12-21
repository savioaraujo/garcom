/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.enums;

/**
 *
 * @author foxinline
 */
public enum TipoTelefone {

    FIXO("Fixo"), CELULAR("Celular");
    private String descricao;

    private TipoTelefone(String nome) {
        this.descricao = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String str() {
        return this.descricao;
    }
}
