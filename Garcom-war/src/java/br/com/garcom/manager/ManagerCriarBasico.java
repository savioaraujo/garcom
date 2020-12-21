/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager;

import br.com.foxinline.manager.BasicManagerCreate;
import br.com.foxinline.model.BasicBean;
import br.com.foxinline.model.UserSystem;
import br.com.foxinline.util.Utils;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.main.ServicoGenericoBasico;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.usuario.servico.UsuarioServico;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author foxinline
 */
public abstract class ManagerCriarBasico<T extends BasicBean> extends BasicManagerCreate {

    protected T entidade;
    protected String editarId;
    @EJB
    protected UsuarioServico usuarioServico;

    @PostConstruct
    protected void init() {
        Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        editarId = parametros.get("id");
        if (!Utils.isEmpty(editarId)) {
            delegar();
        } else {
            instanciar();
        }

    }

    @Override
    public void instanciar() {
        super.instanciar();
        try {
            this.entidade = (T) getTypeParameterClass().newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(ManagerCriarBasico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ManagerCriarBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delegar() {
        super.delegar();
        entidade = (T) getServico().load(editarId);
    }

    public void salvar() {
        if (entidade.getId() == null) {
            getServico().save(entidade);
        } else {
            getServico().update(entidade);
        }

        Mensagem.messagemInfoRedirect("Salvo com sucesso !", "visualizar" + getTypeParameterClass().getSimpleName() + ".xhtml?id=" + entidade.getId());

    }

    @SuppressWarnings("unchecked")
    public Class<T> getTypeParameterClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    protected abstract ServicoGenericoBasico getServico();

    public T getEntidade() {
        return entidade;
    }

    public void setEntidade(T entidade) {
        this.entidade = entidade;
    }

    @Override
    public UserSystem getUserSystem() {
        return usuarioServico.getUserLogged();
    }
}
