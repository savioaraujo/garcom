/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.financeiro.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import br.com.foxinline.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "maquina_cartao")
public class MaquinaCartao extends BasicLoggedBean {

    private String nome;
    @Column(name = "slug_nome")
    private String slugNome;
    private boolean debito;
    private boolean credito;
    @OneToMany(mappedBy = "maquinaCartao", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ItemJuros> itensJuros;
    @Transient
    private List<BandeiraCartao> bandeiras;

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

    public List<ItemJuros> getItensJuros() {
        if (Utils.isEmpty(this.itensJuros)) {
            this.itensJuros = new ArrayList<ItemJuros>();
        }
        return itensJuros;
    }

    public void setItensJuros(List<ItemJuros> itensJuros) {
        this.itensJuros = itensJuros;
    }

    public List<BandeiraCartao> getBandeiras() {
        return bandeiras;
    }

    public void setBandeiras(List<BandeiraCartao> bandeiras) {
        if (Utils.isEmpty(this.bandeiras)) {
            this.bandeiras = new ArrayList<BandeiraCartao>();
        }
        this.bandeiras = bandeiras;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.nome);
        hash = 83 * hash + Objects.hashCode(this.slugNome);
        hash = 83 * hash + (this.debito ? 1 : 0);
        hash = 83 * hash + (this.credito ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.itensJuros);
        hash = 83 * hash + Objects.hashCode(this.bandeiras);
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
        final MaquinaCartao other = (MaquinaCartao) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.slugNome, other.slugNome)) {
            return false;
        }
        if (this.debito != other.debito) {
            return false;
        }
        if (this.credito != other.credito) {
            return false;
        }
        if (!Objects.equals(this.itensJuros, other.itensJuros)) {
            return false;
        }
        if (!Objects.equals(this.bandeiras, other.bandeiras)) {
            return false;
        }
        return true;
    }
}
