/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.usuario.modelo;

import br.com.foxinline.model.UserSystem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author foxinline
 */
@Entity
@Table(name = "usuario")
public class Usuario extends UserSystem {

    @Column(length = 120)
    @NotNull
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres.")
    private String nome;
    @Column(name = "trocar_senha")
    private boolean trocarSenha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isTrocarSenha() {
        return trocarSenha;
    }

    public void setTrocarSenha(boolean trocarSenha) {
        this.trocarSenha = trocarSenha;
    }
}
