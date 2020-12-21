/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.model.Module;
import br.com.garcom.main.ServicoGenericoBasico;
import br.com.garcom.manager.ManagerPesquisarBasico;
import br.com.garcom.servico.ModuleServico;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "ADMINISTRATIVO", pageRedirect = "index.xhtml")
public class ManagerPesquisarModule extends ManagerPesquisarBasico<Module> {

    @EJB
    private ModuleServico moduleServico;
    
    @Override
    protected ServicoGenericoBasico getServico() {
        return moduleServico;
    }
}
