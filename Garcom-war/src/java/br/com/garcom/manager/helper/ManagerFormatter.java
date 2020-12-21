/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.helper;

import br.com.foxinline.util.Utils;
import br.com.garcom.utils.Caracteres;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
public class ManagerFormatter {

    public String formataTelefone(String numero) {
        if (!Utils.isEmpty(numero)) {
            if (numero.length() <= 9) {

                return Caracteres.adicionarMascara(numero, Caracteres.phoneMask);
            } else {
                return Caracteres.adicionarMascara(numero, Caracteres.newPhoneMask);
            }
        }

        return "";
    }
}
