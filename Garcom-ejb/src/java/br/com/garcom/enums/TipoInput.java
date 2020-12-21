/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.enums;

/**
 *
 * @author Sávio Araújo
 */
public enum TipoInput {

    MOEDA("Moeda"),
    DECIMAL("Decimal"),
    NUMERICO("Numérico"),
    TEXTO_GRANDE("Texto Grande"),
    TEXTO_PEQUENO("Texto pequeno"),
    PORCENTAGEM("Porcentagem");
    private String nome;

    private TipoInput(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
