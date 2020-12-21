/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.model.UserGroup;
import br.com.garcom.main.ServicoGenericoBasico;
import br.com.garcom.manager.ManagerPesquisarBasico;
import br.com.garcom.servico.UserGroupServico;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "GRUPOS DE USUARIOS", pageRedirect = "index.xhtml")
public class ManagerPesquisarUserGroup extends ManagerPesquisarBasico<UserGroup> {

    @EJB
    private UserGroupServico userGroupServico;

    @Override
    protected ServicoGenericoBasico getServico() {
        return userGroupServico;
    }
}
