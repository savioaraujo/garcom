/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerPesquisar;
import br.com.garcom.servico.ClienteServico;
import br.com.garcom.basico.modelo.Cliente;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "CLIENTE", pageRedirect = "index.xhtml")
public class ManagerPesquisarCliente extends ManagerPesquisar<Cliente> {

    @EJB
    private ClienteServico clienteServico;
    
    @Override
    protected ServicoGenerico getServico() {
        return clienteServico;
    }
}
