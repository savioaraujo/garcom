/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.basico.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author evaldosavio
 */
@Entity
public class Configuracoes extends BasicLoggedBean {

    private String nome;
    private String endereco;
    private String contatos;
    @Column(name = "caminho_relatorio")
    private String caminhoRelatorio;

    public String getContatos() {
        return contatos;
    }

    public void setContatos(String contatos) {
        this.contatos = contatos;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminhoRelatorio() {
        return caminhoRelatorio;
    }

    public void setCaminhoRelatorio(String caminhoRelatorio) {
        this.caminhoRelatorio = caminhoRelatorio;
    }
}
