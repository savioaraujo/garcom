/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.helper;

import br.com.foxinline.model.Module;
import br.com.foxinline.model.UserGroup;
import br.com.garcom.enums.TipoCliente;
import br.com.garcom.basico.modelo.Cidade;
import br.com.garcom.basico.modelo.Cliente;
import br.com.garcom.estoque.modelo.Produto;
import br.com.garcom.basico.modelo.TipoDocumento;
import br.com.garcom.financeiro.modelo.BandeiraCartao;
import br.com.garcom.financeiro.modelo.TipoMovimentacaoCarteiraLoja;
import br.com.garcom.servico.BandeiraCartaoServico;
import br.com.garcom.servico.CidadeServico;
import br.com.garcom.servico.ClienteServico;
import br.com.garcom.servico.ModuleServico;
import br.com.garcom.servico.ProdutoServico;
import br.com.garcom.servico.TipoDocumentoServico;
import br.com.garcom.servico.TipoMovimentacaoCarteiraLojaServico;
import br.com.garcom.servico.UserGroupServico;
import br.com.garcom.usuario.modelo.Usuario;
import br.com.garcom.usuario.servico.UsuarioServico;
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
public class ManagerAutocomplete {

    @EJB
    ClienteServico clienteServico;
    @EJB
    ProdutoServico produtoServico;
    @EJB
    TipoMovimentacaoCarteiraLojaServico tipoMovimentacaoCarteiraServico;
    @EJB
    TipoDocumentoServico tipoDocumentoServico;
    @EJB
    UsuarioServico usuarioServico;
    @EJB
    CidadeServico cidadeServico;
    @EJB
    BandeiraCartaoServico bandeiraCartaoServico;
    @EJB
    ModuleServico moduleServico;
    @EJB
    UserGroupServico userGroupServico;

    public List<Produto> autocompleteProduto(String sql) {
        return produtoServico.autocompletar(sql);
    }

    public List<Produto> autocompleteProdutoAditivo(String sql) {
        return produtoServico.autocompletarProdutoAditivo(sql);
    }

    
    public List<Usuario> autocompleteUsuario(String sql) {
        return usuarioServico.autocompletar(sql);
    }

    public List<Cliente> autocompleteCliente(String sql) {
        return clienteServico.autocompletar(sql);
    }

    public List<Cliente> autocompleteClienteSemClienteFinal(String sql) {
        return clienteServico.autocompletarSemClienteFinal(sql);
    }

    public List<TipoMovimentacaoCarteiraLoja> autocompleteTipoMovimentacaoCarteira(String sql) {
        return tipoMovimentacaoCarteiraServico.autocompletar(sql);
    }

    public List<TipoDocumento> tipoDocumentoPessoaFisica(String query) {
        return tipoDocumentoServico.autocompletar(query, TipoCliente.PESSOA_FISICA);
    }

    public List<TipoDocumento> tipoDocumentoPessoaJuridica(String query) {
        return tipoDocumentoServico.autocompletar(query, TipoCliente.PESSOA_JURIDICA);
    }

    public List<Cidade> cidade(String query) {
        return cidadeServico.autocompletar(query);
    }

    public List<BandeiraCartao> autocompleteBandeira(String query) {
        return bandeiraCartaoServico.autocompletar(query);
    }
    
    public List<Module> autocompleteModulos(String query) {
        return moduleServico.autocompletar(query);
    }
    
    public List<UserGroup> autocompleteGrupos(String query) {
        return userGroupServico.autocompletar(query);
    }
    
}
