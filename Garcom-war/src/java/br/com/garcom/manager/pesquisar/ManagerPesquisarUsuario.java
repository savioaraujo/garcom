/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.garcom.main.ServicoGenericoBasico;
import br.com.garcom.manager.ManagerPesquisarBasico;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.servico.UsuarioCRUDServico;
import br.com.garcom.usuario.modelo.Usuario;
import br.com.garcom.utils.Caracteres;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "USUARIO", pageRedirect = "index.xhtml")
public class ManagerPesquisarUsuario extends ManagerPesquisarBasico<Usuario> {

    @EJB
    private UsuarioCRUDServico usuarioCRUDServico;
    private String senhaAtual;
    private String novaSenha;
    private String confirmacaoSenha;

    public void alterarSenha() {
        if (Caracteres.criptografarSenha(senhaAtual).equals(this.entidade.getPassword())) {
            if (this.novaSenha.equals(this.confirmacaoSenha)) {
                this.entidade.setPassword(Caracteres.criptografarSenha(this.novaSenha));
                this.usuarioCRUDServico.update(entidade);
                Mensagem.messagemInfoRedirect("Senha alterada com sucesso!", "visualizarUsuario.xhtml?id="+ this.entidade.getId());
            } else {
                Mensagem.messagemWarn("A nova senha esta diferente da confirmação da nova senha!");
            }
        } else {
            Mensagem.messagemWarn("A senha atual esta incorreta!");
        }
    }

    public UsuarioCRUDServico getUsuarioCRUDServico() {
        return usuarioCRUDServico;
    }

    public void setUsuarioCRUDServico(UsuarioCRUDServico usuarioCRUDServico) {
        this.usuarioCRUDServico = usuarioCRUDServico;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    @Override
    protected ServicoGenericoBasico getServico() {
        return usuarioCRUDServico;
    }
}
