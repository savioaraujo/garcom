/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.util.Utils;
import br.com.garcom.estoque.enums.TipoMovimentacaoEstoque;
import br.com.garcom.estoque.exceptions.EstoqueInsuficienteException;
import br.com.garcom.estoque.exceptions.ProdutoIndisponivelException;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.estoque.modelo.ItemMovimentacaoEstoque;
import br.com.garcom.estoque.modelo.Produto;
import br.com.garcom.venda.modelo.Venda;
import br.com.garcom.sql.utils.QueryBuilder;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class ItemMovimentacaoEstoqueServico extends ServicoGenerico<ItemMovimentacaoEstoque> {

    @EJB
    ProdutoServico produtoServico;

    @Override
    public List<ItemMovimentacaoEstoque> pesquisar(ItemMovimentacaoEstoque entidade) {
        return find(entidade);
    }

    /**
     * <p> Registra as movimentaçoes de estoque no sistema, caso nao tenha como
     * movimentar retorna uma erro informando de estoque insuficiente. </p>
     *
     * @param produto
     * @param tipoMovimentacaoEstoque
     * @param quantidade
     * @param venda
     */
    public ItemMovimentacaoEstoque movimentarEstoque(Produto produto, TipoMovimentacaoEstoque tipoMovimentacaoEstoque, Integer quantidade, Venda venda) throws EstoqueInsuficienteException {
        final Date dateAtual = new Date();
        Query query = getEntityManager().createNativeQuery("SELECT * FROM movimentar_estoque( ?1::bigint, ?2::text, ?3::bigint, ?4::bigint, ?5::bigint, ?6::timestamp);");
        // movimentar_estoque ( produto_movimentacao_id, tipo_movimentacao, quantidade_solicitada , venda , usuario , data_hora );
        query.setParameter(1, produto.getId());
        query.setParameter(2, tipoMovimentacaoEstoque.name());
        query.setParameter(3, quantidade);
        if (venda != null) {
            query.setParameter(4, venda.getId());
        } else {
            query.setParameter(4, null);
        }
        query.setParameter(5, getUsuarioLogado().getId());
        query.setParameter(6, dateAtual);
        List<Object[]> result = query.getResultList();
        if (Utils.isEmpty(result)) {
            throw new EJBException("Ocorreu um erro ao movimentar o estoque do produto.");
        } else {
            Object[] retorno = result.get(0);
            if (retorno[0] == null) {
                throw new EstoqueInsuficienteException(String.valueOf(retorno[1]));
            } else {
                query = getEntityManager().createNativeQuery("DELETE FROM retorno_movimentacao WHERE tipo_movimentacao = ?1::text AND data = ?2::timestamp AND usuario_id = ?3::bigint");
                query.setParameter(1, tipoMovimentacaoEstoque.name());
                query.setParameter(2, dateAtual);
                query.setParameter(3, getUsuarioLogado().getId());
                query.executeUpdate();
                return load(String.valueOf(retorno[0]));
            }
        }


    }

    public List<ItemMovimentacaoEstoque> ultimasMovimentacoesProduto(Produto produto) {
        QueryBuilder builder = new QueryBuilder("SELECT item FROM ItemMovimentacaoEstoque item");
        builder.addWhere(" WHERE item.active = TRUE and item.produto = :produto");
        builder.addParam("produto", produto);
        builder.addOrderBy(" ORDER BY item.dataMovimentacao DESC");
        Query query = builder.makeQuery(getEntityManager());
        query.setMaxResults(20);
        return query.getResultList();
    }
}
