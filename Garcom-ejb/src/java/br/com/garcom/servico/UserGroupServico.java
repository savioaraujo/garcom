/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.model.UserGroup;
import br.com.foxinline.util.Utils;
import br.com.garcom.main.ServicoGenericoBasico;
import br.com.garcom.sql.utils.QueryBuilder;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class UserGroupServico extends ServicoGenericoBasico<UserGroup> {

    public List<UserGroup> autocompletar(String texto) {
        UserGroup entidade = new UserGroup();
        entidade.setName(texto);
        return pesquisar(entidade);
    }

    @Override
    public List<UserGroup> pesquisar(UserGroup entidade) {
        QueryBuilder builder = QueryBuilder.instance(UserGroup.class);
        if (!Utils.isEmpty(entidade.getName())) {

            builder.addWhere(" AND lower(userGroup.name) like :name");
            builder.addParam("name", "%" + entidade.getName().toLowerCase().trim() + "%");
        }
        return builder.makeQuery(getEntityManager()).getResultList();
    }
}
