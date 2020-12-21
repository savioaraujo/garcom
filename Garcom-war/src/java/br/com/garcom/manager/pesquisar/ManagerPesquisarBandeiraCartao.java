/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.garcom.financeiro.modelo.BandeiraCartao;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerPesquisar;
import br.com.garcom.servico.BandeiraCartaoServico;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "BANDEIRA CARTÃO", pageRedirect = "index.xhtml")
public class ManagerPesquisarBandeiraCartao extends ManagerPesquisar<BandeiraCartao> {

    @EJB
    private BandeiraCartaoServico bandeiraCartaoServico;
    
    @Override
    protected ServicoGenerico getServico() {
        return bandeiraCartaoServico;
    }
}
