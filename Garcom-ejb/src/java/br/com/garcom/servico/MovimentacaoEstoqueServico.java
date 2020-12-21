/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.util.DateUtils;
import br.com.foxinline.util.Utils;
import br.com.garcom.estoque.modelo.MovimentacaoEstoque;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.sql.utils.QueryBuilder;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class MovimentacaoEstoqueServico extends ServicoGenerico<MovimentacaoEstoque> {

    @Override
    public List<MovimentacaoEstoque> pesquisar(MovimentacaoEstoque entidade) {
        return findAll();
    }

    public List<MovimentacaoEstoque> pesquisar(MovimentacaoEstoque entidade, Date dataInicio, Date dataFinal) {
        QueryBuilder builder = QueryBuilder.instance(klass);
        if (!Utils.isEmpty(entidade.getDocumentoMovimentacao())) {
            builder.addWhere(" AND ( lower(movimentacaoEstoque.documentoMovimentacao) like :descricao OR lower(movimentacaoEstoque.descricao) like :descricao )");
            builder.addParam("descricao", "%" + entidade.getDocumentoMovimentacao().toLowerCase().trim() + "%");
        }

        if (entidade.getUsuarioMovimentacao() != null) {
            builder.addWhere(" AND movimentacaoEstoque.usuarioMovimentacao = :usuario");
            builder.addParam("usuario", entidade.getUsuarioMovimentacao());
        }

        if (dataInicio != null) {
            builder.addWhere(" AND movimentacaoEstoque.dataMovimentacao BETWEEN :dataInicio AND :dataFim");
            builder.addParam("dataInicio", DateUtils.alterTime(dataInicio, 0, 0, 0));
            if (dataFinal == null) {
                dataFinal = dataInicio;
            }
            builder.addParam("dataFim", DateUtils.alterTime(dataFinal, 23, 59, 59));
        }

        return builder.makeQuery(getEntityManager()).getResultList();
    }
}
