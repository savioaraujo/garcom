/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.garcom.financeiro.modelo.MaquinaCartao;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerPesquisar;
import br.com.garcom.servico.MaquinaCartaoServico;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "MAQUINA CARTÃO", pageRedirect = "index.xhtml")
public class ManagerPesquisarMaquinaCartao extends ManagerPesquisar<MaquinaCartao> {

    @EJB
    private MaquinaCartaoServico maquinaCartaoServico;
    private Boolean debito;
    private Boolean credito;

    @Override
    public void pesquisar() {
        this.resultados = maquinaCartaoServico.pesquisar(this.entidade, this.debito, this.credito);
    }

    public Boolean getDebito() {
        return debito;
    }

    public void setDebito(Boolean debito) {
        this.debito = debito;
    }

    public Boolean getCredito() {
        return credito;
    }

    public void setCredito(Boolean credito) {
        this.credito = credito;
    }

    @Override
    protected ServicoGenerico getServico() {
        return maquinaCartaoServico;
    }
}
