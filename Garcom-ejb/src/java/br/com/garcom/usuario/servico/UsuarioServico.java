/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.usuario.servico;

import br.com.foxinline.service.UserService;
import br.com.garcom.usuario.modelo.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author foxinline
 */
@Stateless
public class UsuarioServico extends UserService<Usuario> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public List<Usuario> autocompletar(String sql) {
        return findFilter("nome", sql);
    }
}
