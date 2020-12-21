/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerPesquisar;
import br.com.garcom.servico.TipoMovimentacaoCarteiraLojaServico;
import br.com.garcom.financeiro.modelo.TipoMovimentacaoCarteiraLoja;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "CARTEIRA", pageRedirect = "index.xhtml")
public class ManagerPesquisarTipoMovimentacaoCarteiraLoja extends ManagerPesquisar<TipoMovimentacaoCarteiraLoja> {

    @EJB
    private TipoMovimentacaoCarteiraLojaServico tipoMovimentacaoCarteiraLojaServico;
    
    @Override
    protected ServicoGenerico getServico() {
        return tipoMovimentacaoCarteiraLojaServico;
    }
}
