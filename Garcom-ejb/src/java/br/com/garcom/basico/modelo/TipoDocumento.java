/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.basico.modelo;

import br.com.foxinline.annotation.NotAudited;
import br.com.foxinline.model.BasicLoggedBean;
import br.com.foxinline.util.Utils;
import br.com.garcom.enums.TipoCliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "tipo_documento")
public class TipoDocumento extends BasicLoggedBean {

    private String descricao;
    private String mascara;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = TipoCliente.class)
    @CollectionTable(name = "tipo_documento_obrigatoriedade", joinColumns =
    @JoinColumn(name = "tipo_documento_id"))
    @Column(name = "tipo_pessoa")
    @NotAudited
    private List<TipoCliente> obrigatorioEm;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = TipoCliente.class)
    @CollectionTable(name = "tipo_documento_utilizacao", joinColumns =
    @JoinColumn(name = "tipo_documento_id"))
    @Column(name = "tipo_cadastro")
    @NotAudited
    private List<TipoCliente> utilizadoEm;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tipo_documento_sub_campos", inverseJoinColumns =
    @JoinColumn(name = "sub_campo_id"))
    private List<SubCampo> subCampos;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public List<TipoCliente> getObrigatorioEm() {
        if (Utils.isEmpty(this.obrigatorioEm)) {
            obrigatorioEm = new ArrayList<TipoCliente>();
        }
        return obrigatorioEm;
    }

    public void setObrigatorioEm(List<TipoCliente> obrigatorioEm) {
        this.obrigatorioEm = obrigatorioEm;
    }

    public List<TipoCliente> getUtilizadoEm() {
        if (Utils.isEmpty(this.utilizadoEm)) {
            utilizadoEm = new ArrayList<TipoCliente>();
        }
        return utilizadoEm;
    }

    public void setUtilizadoEm(List<TipoCliente> utilizadoEm) {
        this.utilizadoEm = utilizadoEm;
    }

    public List<SubCampo> getSubCampos() {
        return subCampos;
    }

    public void setSubCampos(List<SubCampo> subCampos) {
        this.subCampos = subCampos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
        hash = 89 * hash + (this.mascara != null ? this.mascara.hashCode() : 0);
        hash = 89 * hash + (this.obrigatorioEm != null ? this.obrigatorioEm.hashCode() : 0);
        hash = 89 * hash + (this.utilizadoEm != null ? this.utilizadoEm.hashCode() : 0);
        hash = 89 * hash + (this.subCampos != null ? this.subCampos.hashCode() : 0);
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
        final TipoDocumento other = (TipoDocumento) obj;
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        if ((this.mascara == null) ? (other.mascara != null) : !this.mascara.equals(other.mascara)) {
            return false;
        }
        if (this.obrigatorioEm != other.obrigatorioEm && (this.obrigatorioEm == null || !this.obrigatorioEm.equals(other.obrigatorioEm))) {
            return false;
        }
        if (this.utilizadoEm != other.utilizadoEm && (this.utilizadoEm == null || !this.utilizadoEm.equals(other.utilizadoEm))) {
            return false;
        }
        if (this.subCampos != other.subCampos && (this.subCampos == null || !this.subCampos.equals(other.subCampos))) {
            return false;
        }
        return true;
    }
}
