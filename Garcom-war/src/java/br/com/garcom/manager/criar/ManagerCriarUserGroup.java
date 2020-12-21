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
import br.com.garcom.servico.UserGroupServico;
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
@AccessPermission(moduleName = "GRUPOS DE USUARIOS", pageRedirect = "index.xhtml")
public class ManagerCriarUserGroup extends ManagerCriarBasico<UserGroup> {

    @EJB
    private UserGroupServico userGroupServico;
    private Access access;
    private Access accessRemocao;

    @Override
    protected void init() {
        super.init();
        instanciarAcesso();
        if (Utils.isEmpty(this.entidade.getAccesses())) {
            this.entidade.setAccesses(new ArrayList<Access>());
        }
    }

    @Override
    public void instanciar() {
        super.instanciar();
    }

    @Override
    public void delegar() {
        super.delegar();
    }

    public void editarAcesso(Access access) {
        this.access = access;
    }

    public void removerAcesso() {
        this.entidade.getAccesses().remove(accessRemocao);
        this.accessRemocao = null;
    }

    public void adicionarAcesso() {
        if (!this.entidade.getAccesses().contains(this.access)) {
            this.entidade.getAccesses().add(access);
        }
        instanciarAcesso();
    }

    public void instanciarAcesso() {
        access = new Access();
        this.access.setSave(true);
        this.access.setEdit(true);
        this.access.setRemove(true);
        this.access.setFind(true);
        this.access.setShow(true);
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public Access getAccessRemocao() {
        return accessRemocao;
    }

    public void setAccessRemocao(Access accessRemocao) {
        this.accessRemocao = accessRemocao;
    }

    @Override
    protected ServicoGenericoBasico getServico() {
        return userGroupServico;
    }
}
