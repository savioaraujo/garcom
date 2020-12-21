/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.main;

import br.com.foxinline.model.BasicBean;
import br.com.foxinline.service.GenericService;
import br.com.foxinline.service.UserService;
import br.com.foxinline.util.Utils;
import br.com.garcom.usuario.modelo.Usuario;
import br.com.garcom.usuario.servico.UsuarioServico;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author foxinline
 */
public abstract class ServicoGenericoBasico<T extends BasicBean> extends GenericService<T> {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private UsuarioServico userService;

    public Usuario getUsuarioLogado() {
        return userService.getUserLogged();
    }

    public UserService getUserService() {
        return userService;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public abstract List<T> pesquisar(T entidade);

    @Override
    public T load(Long id) {
        List<T> result = findFilter("id", id);
        if (!Utils.isEmpty(result)) {
            T entidade = result.get(0);
            getEntityManager().refresh(entidade);
            return entidade;
        } else {
            return null;
        }
    }

    @Override
    public T load(T bean) {
        if (bean != null) {
            return this.load(bean.getId());
        } else {
            return null;
        }
    }
}
