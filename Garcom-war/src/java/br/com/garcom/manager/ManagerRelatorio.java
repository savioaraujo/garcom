/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager;

import br.com.foxinline.manager.BasicManagerCreate;
import br.com.foxinline.model.UserSystem;
import br.com.garcom.usuario.servico.UsuarioServico;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author evaldosavio
 */
public class ManagerRelatorio extends BasicManagerCreate {

    @EJB
    protected UsuarioServico usuarioServico;

    @PostConstruct
    protected void init() {
    }

    @Override
    public UserSystem getUserSystem() {
        return usuarioServico.getUserLogged();
    }
}
