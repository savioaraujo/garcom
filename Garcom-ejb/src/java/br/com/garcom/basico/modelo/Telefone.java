/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.basico.modelo;

import br.com.garcom.enums.TipoTelefone;
import br.com.foxinline.model.BasicBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author foxinline
 */
@Entity
public class Telefone extends BasicBean {

    private String numero;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_telefone")
    private TipoTelefone tipoTelefone;

    public Telefone() {
        this.tipoTelefone = TipoTelefone.CELULAR;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(TipoTelefone tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Telefone other = (Telefone) obj;
        if ((this.numero == null) ? (other.numero != null) : !this.numero.equals(other.numero)) {
            return false;
        }
        if (this.tipoTelefone != other.tipoTelefone) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.numero != null ? this.numero.hashCode() : 0);
        hash = 79 * hash + (this.tipoTelefone != null ? this.tipoTelefone.hashCode() : 0);
        return hash;
    }
}
