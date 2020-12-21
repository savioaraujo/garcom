/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.basico.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import br.com.garcom.enums.TipoInput;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Sávio Araújo
 */
@Entity
@Table(name = "sub_campo")
public class SubCampo extends BasicLoggedBean {

    @Column(length = 50)
    private String nome;
    @Column(name = "tipo_input")
    private TipoInput tipoInput;
    private String mascara;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoInput getTipoInput() {
        return tipoInput;
    }

    public void setTipoInput(TipoInput tipoInput) {
        this.tipoInput = tipoInput;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 73 * hash + (this.tipoInput != null ? this.tipoInput.hashCode() : 0);
        hash = 73 * hash + (this.mascara != null ? this.mascara.hashCode() : 0);
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
        final SubCampo other = (SubCampo) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.tipoInput != other.tipoInput) {
            return false;
        }
        if ((this.mascara == null) ? (other.mascara != null) : !this.mascara.equals(other.mascara)) {
            return false;
        }
        return true;
    }
}
