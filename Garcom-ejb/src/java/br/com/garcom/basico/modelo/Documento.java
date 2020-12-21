/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.basico.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import br.com.foxinline.util.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name="documento")
public class Documento extends BasicLoggedBean{
    
    
    @OneToOne
    @JoinColumn(name = "tipo_documento_id")
    private TipoDocumento tipoDocumento;
    @Column(length = 100)
    private String numero;
    @OneToMany(cascade= CascadeType.ALL)
    @JoinTable(name = "documento_sub_campos", inverseJoinColumns =
    @JoinColumn(name = "sub_campo_documento_id"))
    private List<SubCampoDocumento> subCampos;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "data_documento")
    private Date dataDocumento;

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public List<SubCampoDocumento> getSubCampos() {
        if(Utils.isEmpty(subCampos)){
            this.subCampos = new ArrayList<>();
        }
        return subCampos;
    }

    public void setSubCampos(List<SubCampoDocumento> subCampos) {
        this.subCampos = subCampos;
    }

    public Date getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.tipoDocumento != null ? this.tipoDocumento.hashCode() : 0);
        hash = 17 * hash + (this.numero != null ? this.numero.hashCode() : 0);
        hash = 17 * hash + (this.subCampos != null ? this.subCampos.hashCode() : 0);
        hash = 17 * hash + (this.dataDocumento != null ? this.dataDocumento.hashCode() : 0);
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
        final Documento other = (Documento) obj;
        if (this.tipoDocumento != other.tipoDocumento && (this.tipoDocumento == null || !this.tipoDocumento.equals(other.tipoDocumento))) {
            return false;
        }
        if ((this.numero == null) ? (other.numero != null) : !this.numero.equals(other.numero)) {
            return false;
        }
        if (this.subCampos != other.subCampos && (this.subCampos == null || !this.subCampos.equals(other.subCampos))) {
            return false;
        }
        if (this.dataDocumento != other.dataDocumento && (this.dataDocumento == null || !this.dataDocumento.equals(other.dataDocumento))) {
            return false;
        }
        return true;
    }
    
    

}
