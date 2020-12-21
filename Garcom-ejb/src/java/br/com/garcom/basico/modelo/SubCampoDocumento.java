/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.basico.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sávio Araújo
 */
@Entity
@Table(name="sub_campo_documento")
public class SubCampoDocumento extends BasicLoggedBean {

    @Column(length = 255)
    private String valor;
    @ManyToOne
    @JoinColumn(name = "sub_campo_id")
    private SubCampo subCampo;

    public SubCampoDocumento(String valor, SubCampo subCampo) {
        this.valor = valor;
        this.subCampo = subCampo;
    }

    public SubCampoDocumento() {
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public SubCampo getSubCampo() {
        return subCampo;
    }

    public void setSubCampo(SubCampo subCampo) {
        this.subCampo = subCampo;
    }
}
