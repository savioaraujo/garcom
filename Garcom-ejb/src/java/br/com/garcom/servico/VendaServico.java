/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.util.DateUtils;
import br.com.foxinline.util.Utils;
import br.com.garcom.enums.venda.StatusVenda;
import br.com.garcom.enums.venda.TipoVenda;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.basico.modelo.Cliente;
import br.com.garcom.estoque.modelo.Produto;
import br.com.garcom.venda.modelo.Venda;
import br.com.garcom.venda.relatorio.vo.ItemVendaVO;
import br.com.garcom.sql.utils.QueryBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class VendaServico extends ServicoGenerico<Venda> {

    @EJB
    private ProdutoServico produtoServico;

    @Override
    public List<Venda> pesquisar(Venda entidade) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Venda> pesquisar(Venda venda, Date dataInicio, Date dataFim) {
        QueryBuilder builder = new QueryBuilder("SELECT venda FROM Venda venda");
        builder.addWhere("WHERE venda.active = TRUE");
        if (dataInicio != null) {
            builder.addWhere(" AND venda.dataFechamento BETWEEN :dataInicio AND :dataFim ");
            if (dataFim == null) {
                dataFim = dataInicio;
            }
            builder.addParam("dataInicio", DateUtils.alterTime(dataInicio, 0, 0, 0));
            builder.addParam("dataFim", DateUtils.alterTime(dataFim, 23, 59, 59));
        }

        if (venda.getCliente() != null) {
            builder.addWhere(" AND venda.cliente = :cliente");
            builder.addParam("cliente", venda.getCliente());
        }

        if (venda.getUsuarioAbertura() != null) {
            builder.addWhere(" AND  venda.usuarioAbertura = :usuarioAbertura");
            builder.addParam("usuarioAbertura", venda.getUsuarioAbertura());
        }

        if (venda.getCodigo() != null) {
            builder.addWhere(" AND  venda.codigo = :codigo");
            builder.addParam("codigo", venda.getCodigo());
        }

        if (venda.getTipoVenda() != null) {
            builder.addWhere(" AND  venda.tipoVenda = :tipo");
            builder.addParam("tipo", venda.getTipoVenda());
        }

        if (venda.getUsuarioFechamento() != null) {
            builder.addWhere(" AND  venda.usuarioFechamento = :usuarioFechamento");
            builder.addParam("usuarioFechamento", venda.getUsuarioFechamento());
        }
        builder.addOrderBy("ORDER BY venda.codigo");
        Query query = builder.makeQuery(getEntityManager());
        query.setMaxResults(2000);
        return query.getResultList();
    }

    public Venda buscarVendaAbertaPorCliente(Cliente cliente, TipoVenda tipoVenda) {
        if (cliente == null) {
            return null;
        }
        QueryBuilder builder = new QueryBuilder("SELECT venda FROM Venda venda");
        builder.addJoin(" INNER JOIN venda.cliente cliente");
        builder.addWhere(" WHERE cliente = :cliente AND venda.active = TRUE AND venda.aberta = TRUE ");
        builder.addParam("cliente", cliente);
        builder.addWhere(" AND venda.tipoVenda = :tipoVenda");
        builder.addParam("tipoVenda", tipoVenda);
        builder.addWhere(" AND venda.statusVenda = :statusAberto");
        builder.addParam("statusAberto", StatusVenda.ABERTA);
        Query query = builder.makeQuery(getEntityManager());
        List<Venda> resultList = query.getResultList();
        if (Utils.isEmpty(resultList)) {
            return null;
        } else {
            Venda venda = resultList.get(0);
            venda = load(venda);
            return venda;
        }
    }

    public List<Venda> buscarVendaAberta() {

        String sql = "SELECT venda FROM Venda venda "
                + " WHERE venda.active = TRUE AND venda.aberta = TRUE AND venda.tipoVenda = :tipo"
                + " ORDER BY venda.dataAbertura";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter("tipo", TipoVenda.VENDA);
        return query.getResultList();
    }

    public List<Venda> relatorioVendasPorPeriodo(Date dataInicial, Date dataFinal) {
        String sql = "SELECT venda FROM Venda venda"
                + " WHERE venda.active = TRUE AND venda.aberta = FALSE AND venda.dataFechamento BETWEEN :dataInicio AND :dataFinal"
                + " ORDER BY venda.codigo";
        if (dataFinal == null) {
            dataFinal = dataInicial;
        }

        dataInicial = DateUtils.alterTime(dataInicial, 0, 0, 0);
        dataFinal = DateUtils.alterTime(dataFinal, 23, 59, 59);
        Query query = getEntityManager().createQuery(sql);
        query.setParameter("dataInicio", dataInicial);
        query.setParameter("dataFinal", dataFinal);

        return query.getResultList();
    }

    public List<Venda> relatorioVendasPorCliente(Date dataInicial, Date dataFinal, Cliente cliente) {
        String sql = "SELECT venda FROM Venda venda"
                + " WHERE venda.active = TRUE AND venda.aberta = FALSE AND venda.dataFechamento BETWEEN :dataInicio AND :dataFinal AND venda.cliente = :cliente "
                + " ORDER BY venda.codigo";

        if (dataFinal == null) {
            dataFinal = dataInicial;
        }

        dataInicial = DateUtils.alterTime(dataInicial, 0, 0, 0);
        dataFinal = DateUtils.alterTime(dataFinal, 23, 59, 59);
        Query query = getEntityManager().createQuery(sql);
        query.setParameter("dataInicio", dataInicial);
        query.setParameter("dataFinal", dataFinal);
        query.setParameter("cliente", cliente);

        return query.getResultList();
    }

    public List<ItemVendaVO> relatorioVendasProdutosMaisVendidos(Date dataInicial, Date dataFinal) {
        String sql = "	SELECT id, descricao, quantidade, valor_total"
                + "	FROM("
                + "		SELECT id, descricao, sum(quantidade) quantidade, sum(valor_total) valor_total"
                + "		FROM ("
                + "			SELECT item_venda.produto_id id, produto.descricao descricao, SUM(item_venda.quantidade) quantidade, SUM(item_venda.valor_total) valor_total"
                + "			FROM item_venda"
                + "			INNER JOIN venda ON venda.id = item_venda.venda_id"
                + "			INNER JOIN produto ON produto.id = item_venda.produto_id"
                + "			WHERE venda.data_fechamento BETWEEN ?1 AND ?2 AND venda.active = TRUE"
                + "			GROUP BY item_venda.produto_id, produto.descricao"
                + "			UNION	"
                + "			SELECT null id, item_venda.descricao_produto descricao, SUM(item_venda.quantidade) quantidade, SUM(item_venda.valor_total) valor_total"
                + "			FROM item_venda"
                + "			INNER JOIN venda ON venda.id = item_venda.venda_id"
                + "			WHERE venda.data_fechamento BETWEEN ?1 AND ?2 AND venda.active = TRUE AND item_venda.produto_id IS NULL"
                + "			GROUP BY item_venda.descricao_produto"
                + "			UNION"
                + "			SELECT aditivo.produto_id id, produto.descricao descricao, SUM(aditivo.quantidade) quantidade, SUM(aditivo.valor_total) valor_total"
                + "			FROM item_venda"
                + "			INNER JOIN venda ON venda.id = item_venda.venda_id"
                + "			INNER JOIN item_venda_item_aditivo _item_venda_aditivos ON _item_venda_aditivos.item_venda_id = item_venda.id"
                + "			INNER JOIN item_aditivo_venda aditivo ON aditivo.id = _item_venda_aditivos.item_aditivo_venda_id "
                + "			INNER JOIN produto ON produto.id = aditivo.produto_id"
                + "			WHERE venda.data_fechamento BETWEEN ?1 AND ?2 AND venda.active = TRUE AND not aditivo.retornavel"
                + "			GROUP BY aditivo.produto_id, produto.descricao"
                + "		) as relatorio"
                + "		GROUP BY id, descricao "
                + "	) as relatorio_formatado"
                + "	ORDER BY quantidade DESC, valor_total DESC, descricao ASC";

        if (dataFinal == null) {
            dataFinal = dataInicial;
        }

        dataInicial = DateUtils.alterTime(dataInicial, 0, 0, 0);
        dataFinal = DateUtils.alterTime(dataFinal, 23, 59, 59);
        Query query = getEntityManager().createNativeQuery(sql);
        query.setParameter(1, dataInicial);
        query.setParameter(2, dataFinal);
        List<Object[]> resultList = query.getResultList();
        List<ItemVendaVO> itemVendaVOs = new ArrayList<ItemVendaVO>();
        if (!Utils.isEmpty(resultList)) {
            for (Object[] item : resultList) {
                ItemVendaVO itemVo = new ItemVendaVO();
                final Produto produto = new Produto();
                produto.setDescricao((String) item[1]);
                itemVo.setProduto(produto);
                itemVo.setQuantidade(Long.valueOf(String.valueOf(item[2])));
                itemVo.setTotal(new BigDecimal(String.valueOf(item[3])));
                itemVendaVOs.add(itemVo);
            }
        }

        return itemVendaVOs;
    }
}
