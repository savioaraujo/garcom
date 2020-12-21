/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.util.Utils;
import br.com.garcom.enums.FormaPagamento;
import br.com.garcom.financeiro.modelo.BandeiraCartao;
import br.com.garcom.financeiro.modelo.MaquinaCartao;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.sql.utils.QueryBuilder;
import br.com.garcom.utils.Caracteres;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class BandeiraCartaoServico extends ServicoGenerico<BandeiraCartao> {

    public List<BandeiraCartao> autocompletar(String texto) {
        QueryBuilder builder = QueryBuilder.instance(BandeiraCartao.class);
        if (!Utils.isEmpty(texto)) {
            builder.addWhere(" AND lower(bandeiraCartao.slugNome) LIKE :nome");
            builder.addParam("nome", "%" + Caracteres.removeCaracteresEspeciais(texto).toLowerCase() + "%");
        }
        Query query = builder.makeQuery(getEntityManager());
        return query.getResultList();
    }

    private void setarSlugNome(BandeiraCartao bandeiraCartao) {
        bandeiraCartao.setSlugNome(Caracteres.removeCaracteresEspeciais(bandeiraCartao.getNome()));
    }

    @Override
    public void save(BandeiraCartao bean) {
        setarSlugNome(bean);
        super.save(bean);
    }

    @Override
    public BandeiraCartao update(BandeiraCartao bean) {
        setarSlugNome(bean);
        return super.update(bean);
    }

    @Override
    public List<BandeiraCartao> pesquisar(BandeiraCartao entidade) {
        return autocompletar(entidade.getNome());
    }

    public List<BandeiraCartao> obterBandeirasDisponiveisMaquina(MaquinaCartao maquinaCartao, FormaPagamento formaPagamento) {
        QueryBuilder builder = new QueryBuilder("SELECT DISTINCT itemJuros.bandeira FROM MaquinaCartao maquinaCartao "
                + " INNER JOIN maquinaCartao.itensJuros itemJuros "
                + " INNER JOIN itemJuros.bandeira bandeira "
                + " WHERE maquinaCartao.active = TRUE AND itemJuros.active = TRUE");
        builder.addWhere(" AND maquinaCartao.id = :id");
        builder.addParam("id", maquinaCartao.getId());
        if (formaPagamento != null) {
            if (formaPagamento.equals(FormaPagamento.CARTAO_DEBITO)) {
                builder.addWhere(" AND bandeira.debito = TRUE ");
            } else if (formaPagamento.equals(FormaPagamento.CARTAO_CREDITO)) {
                builder.addWhere(" AND bandeira.credito = TRUE ");
            }
        }
        return builder.makeQuery(getEntityManager()).getResultList();
    }
}
