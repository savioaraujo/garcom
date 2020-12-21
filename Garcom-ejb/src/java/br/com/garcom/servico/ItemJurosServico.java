/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.garcom.financeiro.modelo.BandeiraCartao;
import br.com.garcom.financeiro.modelo.ItemJuros;
import br.com.garcom.financeiro.modelo.MaquinaCartao;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.sql.utils.QueryBuilder;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class ItemJurosServico extends ServicoGenerico<ItemJuros> {

    public List<ItemJuros> autocompletar(String texto) {
        return findAll();
    }

    @Override
    public List<ItemJuros> pesquisar(ItemJuros entidade) {
        return findAll();
    }

    public List<ItemJuros> obterParcelamentoDisponiveis(MaquinaCartao maquinaCartao, BandeiraCartao bandeiraCartao) {
        QueryBuilder builder = QueryBuilder.instance(klass);
        builder.addWhere(" AND itemJuros.maquinaCartao = :maquina");
        builder.addParam("maquina", maquinaCartao);
        builder.addWhere(" AND itemJuros.bandeira = :bandeira");
        builder.addParam("bandeira", bandeiraCartao);
        builder.addOrderBy(" ORDER BY itemJuros.parcelamento");
        return builder.makeQuery(getEntityManager()).getResultList();
    }
}
