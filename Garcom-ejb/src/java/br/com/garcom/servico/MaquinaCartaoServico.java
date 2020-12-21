/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.util.StringUtils;
import br.com.foxinline.util.Utils;
import br.com.garcom.financeiro.modelo.MaquinaCartao;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.sql.utils.QueryBuilder;
import br.com.garcom.utils.Caracteres;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class MaquinaCartaoServico extends ServicoGenerico<MaquinaCartao> {

    public List<MaquinaCartao> autocompletar(String texto) {
        return findAll();
    }

    @Override
    public List<MaquinaCartao> pesquisar(MaquinaCartao entidade) {
        return findAll();
    }

    @Override
    public void save(MaquinaCartao bean) {
        bean.setSlugNome(StringUtils.removeCaracteresEspeciais(bean.getNome()));
        super.save(bean);
    }

    @Override
    public MaquinaCartao update(MaquinaCartao bean) {
        bean.setSlugNome(StringUtils.removeCaracteresEspeciais(bean.getNome()));
        return super.update(bean);
    }

    public List<MaquinaCartao> pesquisar(MaquinaCartao entidade, Boolean debito, Boolean credito) {

        QueryBuilder queryBuilder = QueryBuilder.instance(klass);
        if (entidade != null && !Utils.isEmpty(entidade.getNome())) {
            queryBuilder.addWhere(" AND lower(maquinaCartao.slugNome) like :nome");
            queryBuilder.addParam("nome", "%" + entidade.getNome().toLowerCase().trim() + "%");
        }

        if (debito != null) {
            queryBuilder.addWhere(" AND maquinaCartao.debito = :debito");
            queryBuilder.addParam("debito", debito);
        }

        if (credito != null) {
            queryBuilder.addWhere(" AND maquinaCartao.credito = :credito");
            queryBuilder.addParam("credito", credito);
        }

        return queryBuilder.makeQuery(getEntityManager()).getResultList();
    }
}