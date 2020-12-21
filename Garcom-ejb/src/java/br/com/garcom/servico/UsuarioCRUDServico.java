/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.util.Utils;
import br.com.garcom.main.ServicoGenericoBasico;
import br.com.garcom.sql.utils.QueryBuilder;
import br.com.garcom.usuario.modelo.Usuario;
import br.com.garcom.utils.Caracteres;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class UsuarioCRUDServico extends ServicoGenericoBasico<Usuario> {

    public List<Usuario> autocompletar(String texto) {
        return findAll();
    }

    @Override
    public void save(Usuario bean) {
        bean.setPassword(Caracteres.criptografarSenha(bean.getPassword()));
        super.save(bean);
    }

    @Override
    public Usuario update(Usuario bean) {
        return super.update(bean);
    }

    @Override
    public List<Usuario> pesquisar(Usuario entidade) {
        QueryBuilder builder = QueryBuilder.instance(Usuario.class);
        if (!Utils.isEmpty(entidade.getLogin())) {
            builder.addWhere(" AND lower(usuario.login) like :login");
            builder.addParam("login", "%" + entidade.getLogin().toLowerCase().trim() + "%");
        }

        if (!Utils.isEmpty(entidade.getNome())) {
            builder.addWhere(" AND lower(usuario.nome) like :nome");
            builder.addParam("nome", "%" + entidade.getLogin().toLowerCase().trim() + "%");
        }
        return builder.makeQuery(getEntityManager()).getResultList();
    }
}
