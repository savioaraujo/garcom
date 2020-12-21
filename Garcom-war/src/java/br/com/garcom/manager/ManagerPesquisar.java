/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager;

import br.com.foxinline.manager.BasicManagerSearch;
import br.com.foxinline.model.BasicLoggedBean;
import br.com.foxinline.model.UserSystem;
import br.com.foxinline.util.Utils;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.usuario.servico.UsuarioServico;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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
public abstract class ManagerPesquisar<T extends BasicLoggedBean> extends BasicManagerSearch {

    protected T entidade;
    protected T entidadeRemocao;
    protected List<T> resultados;
    protected String visualizarId;
    @EJB
    protected UsuarioServico usuarioServico;

    @PostConstruct
    protected void init() {
        Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        visualizarId = parametros.get("id");
        if (!Utils.isEmpty(visualizarId)) {
            delegar();
        } else {
            instanciar();
        }

    }

    @Override
    public void instanciar() {
        super.instanciar();
        limpar();
    }

    @Override
    public void delegar() {
        super.delegar();
        entidade = (T) getServico().load(visualizarId);
        if (entidade == null) {
            Mensagem.redirect("pesquisar" + getTypeParameterClass().getSimpleName() + ".xhtml");
        }
    }

    @Override
    public boolean delete() {
        if (super.delete()) {
            getServico().inactivate(entidade);
            Mensagem.messagemInfoRedirect("Exclusão realizada com sucesso!", "pesquisar" + getTypeParameterClass().getSimpleName() + ".xhtml");
            return true;
        }
        return false;
    }

    @Override
    public UserSystem getUserSystem() {
        return usuarioServico.getUserLogged();
    }

    public void limpar() {
        try {
            entidade = getTypeParameterClass().newInstance();
            resultados = new ArrayList<T>();
        } catch (InstantiationException ex) {
            Logger.getLogger(ManagerPesquisar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ManagerPesquisar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisar() {
        resultados = getServico().pesquisar(entidade);
    }

    @SuppressWarnings("unchecked")
    public Class<T> getTypeParameterClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    public T getEntidade() {
        return entidade;
    }

    public void setEntidade(T entidade) {
        this.entidade = entidade;
    }

    public T getEntidadeRemocao() {
        return entidadeRemocao;
    }

    public void setEntidadeRemocao(T entidadeRemocao) {
        this.entidadeRemocao = entidadeRemocao;
    }

    public List<T> getResultados() {
        return resultados;
    }

    public void setResultados(List<T> resultados) {
        this.resultados = resultados;
    }

    public String getVisualizarId() {
        return visualizarId;
    }

    public void setVisualizarId(String visualizarId) {
        this.visualizarId = visualizarId;
    }

    protected abstract ServicoGenerico getServico();
}
