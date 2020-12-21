/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.garcom.enums.TipoCliente;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerPesquisar;
import br.com.garcom.basico.modelo.TipoDocumento;
import br.com.garcom.servico.TipoDocumentoServico;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "ADMINISTRATIVO", pageRedirect = "index.xhtml")
public class ManagerPesquisarTipoDocumento extends ManagerPesquisar<TipoDocumento> {

    @EJB
    private TipoDocumentoServico tipoDocumentoServico;
    private DualListModel<TipoCliente> dualListObrigatorioEm;
    private DualListModel<TipoCliente> dualListDisponivelEm;
    private TipoCliente utilizado;
    private TipoCliente obrigatorio;

    @Override
    protected void init() {
        super.init();

        List<TipoCliente> source = new ArrayList<TipoCliente>();
        source.addAll(Arrays.asList(TipoCliente.values()));

        dualListObrigatorioEm = new DualListModel<TipoCliente>(source, this.entidade.getObrigatorioEm());
        dualListDisponivelEm = new DualListModel<TipoCliente>(source, this.entidade.getUtilizadoEm());
    }

    public DualListModel<TipoCliente> getDualListObrigatorioEm() {
        return dualListObrigatorioEm;
    }

    public void setDualListObrigatorioEm(DualListModel<TipoCliente> dualListObrigatorioEm) {
        this.dualListObrigatorioEm = dualListObrigatorioEm;
    }

    public DualListModel<TipoCliente> getDualListDisponivelEm() {
        return dualListDisponivelEm;
    }

    public void setDualListDisponivelEm(DualListModel<TipoCliente> dualListDisponivelEm) {
        this.dualListDisponivelEm = dualListDisponivelEm;
    }

    public TipoCliente getObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(TipoCliente obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public TipoCliente getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(TipoCliente utilizado) {
        this.utilizado = utilizado;
    }

    @Override
    public void pesquisar() {
        resultados = tipoDocumentoServico.pesquisar(this.entidade, utilizado, obrigatorio);
    }

    @Override
    protected ServicoGenerico getServico() {
        return tipoDocumentoServico;
    }
}
