/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.criar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.Utils;
import br.com.garcom.enums.TipoCliente;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerCriar;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.basico.modelo.SubCampo;
import br.com.garcom.basico.modelo.TipoDocumento;
import br.com.garcom.servico.TipoDocumentoServico;
import java.util.ArrayList;
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
public class ManagerCriarTipoDocumento extends ManagerCriar<TipoDocumento> {

    @EJB
    private TipoDocumentoServico tipoDocumentoServico;
    private DualListModel<TipoCliente> dualListObrigatorioEm;
    private DualListModel<TipoCliente> dualListDisponivelEm;
    private SubCampo subCampo;

    @Override
    protected void init() {
        super.init();
        iniciarSubCampos();
        List<TipoCliente> source = new ArrayList<TipoCliente>();
        source.add(TipoCliente.PESSOA_FISICA);
        source.add(TipoCliente.PESSOA_JURIDICA);

        dualListObrigatorioEm = new DualListModel<TipoCliente>(source, this.entidade.getObrigatorioEm());
        dualListDisponivelEm = new DualListModel<TipoCliente>(source, this.entidade.getUtilizadoEm());
    }

    @Override
    public void salvar() {

        this.entidade.setUtilizadoEm(dualListDisponivelEm.getTarget());
        this.entidade.setObrigatorioEm(dualListObrigatorioEm.getTarget());
        super.salvar();
    }

    public void adicionarSubCampo() {

        if (Utils.isEmpty(this.subCampo.getNome())) {
            Mensagem.messagemError("Descrição do SubCampo é obrigatório !");
            return;
        }

        if (!this.entidade.getSubCampos().contains(this.subCampo)) {
            this.entidade.getSubCampos().add(this.subCampo);
        }

        iniciarSubCampo();
    }

    public void iniciarSubCampo() {
        this.subCampo = new SubCampo();
    }

    private void iniciarSubCampos() {
        iniciarSubCampo();

        if (Utils.isEmpty(this.entidade.getSubCampos())) {
            this.entidade.setSubCampos(new ArrayList<SubCampo>());
        }
    }

    public void editarSubCampo(SubCampo subCampo) {
        this.subCampo = subCampo;
        removerSubCampo(subCampo);
    }

    public void removerSubCampo(SubCampo subCampo) {
        this.entidade.getSubCampos().remove(subCampo);
    }

    public DualListModel<TipoCliente> getDualListObrigatorioEm() {
        return dualListObrigatorioEm;
    }

    public SubCampo getSubCampo() {
        return subCampo;
    }

    public void setSubCampo(SubCampo subCampo) {
        this.subCampo = subCampo;
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

    @Override
    protected ServicoGenerico getServico() {
        return tipoDocumentoServico;
    }
}
