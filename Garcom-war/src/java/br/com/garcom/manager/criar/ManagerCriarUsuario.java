/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.criar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.model.Access;
import br.com.foxinline.model.UserGroup;
import br.com.foxinline.util.Utils;
import br.com.garcom.main.ServicoGenericoBasico;
import br.com.garcom.manager.ManagerCriarBasico;
import br.com.garcom.servico.UsuarioCRUDServico;
import br.com.garcom.usuario.modelo.Usuario;
import java.util.ArrayList;
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
public class ManagerCriarUsuario extends ManagerCriarBasico<Usuario> {

    @EJB
    private UsuarioCRUDServico usuarioCRUDServico;
    private UserGroup userGroup;
    private UserGroup grupo;
    private UserGroup grupoRemocao;

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void instanciar() {
        super.instanciar();
        if (Utils.isEmpty(this.entidade.getGroup())) {
            this.entidade.setGroup(new ArrayList<UserGroup>());
        }
    }

    @Override
    public void delegar() {
        super.delegar();
        if (Utils.isEmpty(this.entidade.getGroup())) {
            this.entidade.setGroup(new ArrayList<UserGroup>());
        }
    }

    public void editarGrupo(UserGroup grupo) {
        this.grupo = grupo;
    }

    public void removerGrupo() {
        this.entidade.getGroup().remove(this.grupoRemocao);
        this.grupoRemocao = null;
    }

    public void adicionarGrupo() {
        this.entidade.getGroup().add(this.grupo);
        instanciarGrupo();
    }

    public void instanciarGrupo() {
        this.grupo = new UserGroup();
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public UserGroup getGrupo() {
        return grupo;
    }

    public void setGrupo(UserGroup grupo) {
        this.grupo = grupo;
    }

    public UserGroup getGrupoRemocao() {
        return grupoRemocao;
    }

    public void setGrupoRemocao(UserGroup grupoRemocao) {
        this.grupoRemocao = grupoRemocao;
    }

    @Override
    protected ServicoGenericoBasico getServico() {
        return this.usuarioCRUDServico;
    }
}
