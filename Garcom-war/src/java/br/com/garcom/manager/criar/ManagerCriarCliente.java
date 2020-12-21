/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.criar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.StringUtils;
import br.com.foxinline.util.Utils;
import br.com.garcom.enums.TipoCliente;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerCriar;
import br.com.garcom.basico.modelo.Bairro;
import br.com.garcom.basico.modelo.Cliente;
import br.com.garcom.basico.modelo.Documento;
import br.com.garcom.basico.modelo.Endereco;
import br.com.garcom.basico.modelo.SubCampo;
import br.com.garcom.basico.modelo.SubCampoDocumento;
import br.com.garcom.basico.modelo.Telefone;
import br.com.garcom.basico.modelo.TipoDocumento;
import br.com.garcom.servico.BairroServico;
import br.com.garcom.servico.ClienteServico;
import br.com.garcom.servico.TipoDocumentoServico;
import java.util.List;
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
public class ManagerCriarCliente extends ManagerCriar<Cliente> {

    private Telefone telefone;
    private Telefone telefoneRemocao;
    private Documento documento;
    @EJB
    private ClienteServico clienteServico;
    @EJB
    private TipoDocumentoServico tipoDocumentoServico;
    @EJB
    BairroServico bairroServico;

    @Override
    protected void init() {
        super.init();
        this.telefone = new Telefone();
        instanciarDocumento();
        instanciarSubCampos();
    }

    @Override
    public void instanciar() {
        super.instanciar();
        this.entidade.setTipo(TipoCliente.PESSOA_FISICA);
        this.entidade.setEndereco(new Endereco());
    }

    @Override
    public void delegar() {
        super.delegar();
        if (entidade.getTipo() == null) {
            this.entidade.setTipo(TipoCliente.PESSOA_FISICA);
        }
        if (entidade.getEndereco() == null) {
            this.entidade.setEndereco(new Endereco());
        }
    }

    @Override
    protected ServicoGenerico getServico() {
        return clienteServico;
    }

    public void instanciarDocumento() {
        this.documento = new Documento();
    }

    public void instanciarSubCampos() {
        if (this.documento != null && this.documento.getTipoDocumento() != null && !Utils.isEmpty(this.documento.getTipoDocumento().getSubCampos())) {
            this.documento.setSubCampos(null);
            for (SubCampo subCampo : this.documento.getTipoDocumento().getSubCampos()) {
                this.documento.getSubCampos().add(new SubCampoDocumento("", subCampo));
            }
        } else {
            this.documento.setSubCampos(null);
        }
    }

    public void adicionarTelefone() {
        if (!this.entidade.getTelefones().contains(this.telefone)) {
            this.telefone.setNumero(StringUtils.removeCaracteresEspeciaispor(telefone.getNumero(), ""));
            this.telefone.setNumero(this.telefone.getNumero().replace("(", "").replace(")", ""));
            this.entidade.getTelefones().add(telefone);
        }
        this.telefone = new Telefone();
    }

    public void adicionarDocumento() {
        if (!this.entidade.getDocumentos().contains(this.documento)) {
            this.entidade.getDocumentos().add(this.documento);
        }

        this.documento = new Documento();
    }

    public void removerDocumento(Documento documento) {
        this.entidade.getDocumentos().remove(documento);
    }

    public void removerTelefone() {

        this.entidade.getTelefones().remove(this.telefoneRemocao);
        this.telefoneRemocao = new Telefone();
    }

    public void editarTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public List<TipoDocumento> tipoDocumento(String query) {
        return tipoDocumentoServico.autocompletar(query, this.entidade.getTipo());
    }

    public List<Bairro> bairro(String query) {
        return bairroServico.autocompletar(query, this.entidade.getEndereco().getCidade());
    }

    public void instanciarTelefone() {
        this.telefone = new Telefone();
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Telefone getTelefoneRemocao() {
        return telefoneRemocao;
    }

    public void setTelefoneRemocao(Telefone telefoneRemocao) {
        this.telefoneRemocao = telefoneRemocao;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
}
