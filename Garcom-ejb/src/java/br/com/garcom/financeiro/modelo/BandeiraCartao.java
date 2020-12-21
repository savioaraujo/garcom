/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.financeiro.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "bandeira_cartao")
public class BandeiraCartao extends BasicLoggedBean {

    private String nome;
    @Column(name = "slug_nome")
    private String slugNome;
    private boolean debito;
    private boolean credito;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSlugNome() {
        return slugNome;
    }

    public void setSlugNome(String slugNome) {
        this.slugNome = slugNome;
    }

    public boolean isDebito() {
        return debito;
    }

    public void setDebito(boolean debito) {
        this.debito = debito;
    }

    public boolean isCredito() {
        return credito;
    }

    public void setCredito(boolean credito) {
        this.credito = credito;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.nome);
        hash = 89 * hash + Objects.hashCode(this.slugNome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BandeiraCartao other = (BandeiraCartao) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.slugNome, other.slugNome)) {
            return false;
        }
        return true;
    }
}
