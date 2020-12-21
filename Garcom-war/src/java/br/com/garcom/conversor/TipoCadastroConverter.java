/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.conversor;

import br.com.garcom.enums.TipoCliente;
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sávio Araújo
 */
@FacesConverter(value = "TipoCadastroConverter")
public class TipoCadastroConverter extends EnumConverter {

    public TipoCadastroConverter() {
        super(TipoCliente.class);
    }
}
