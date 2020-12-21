/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.util.StringUtils;
import br.com.foxinline.util.Utils;
import br.com.garcom.estoque.enums.UtilizacaoAditivoProduto;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.estoque.modelo.Produto;
import java.lang.Object;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class ProdutoServico extends ServicoGenerico<Produto> {

    public List<Produto> autocompletar(String texto) {
        String sql = "SELECT produto FROM Produto produto WHERE produto.active = TRUE AND produto.disponivel = TRUE ";
        sql += " AND ( lower(produto.codigo) like :texto OR lower(produto.slugDescricao) LIKE :texto )";


        Query query = getEntityManager().createQuery(sql + " ORDER BY produto.slugDescricao");
        query.setParameter("texto", "%" + StringUtils.removeCaracteresEspeciais(texto).toLowerCase().trim() + "%");

        return query.getResultList();
    }

    public List<Produto> autocompletarProdutoAditivo(String texto) {
        String sql = "SELECT produto FROM Produto produto WHERE produto.active = TRUE AND produto.disponivel = TRUE "
                + " AND (produto.utilizacaoAditivo = :aditivo1 OR produto.utilizacaoAditivo = :aditivo2) ";
        sql += " AND ( lower(produto.codigo) like :texto OR lower(produto.slugDescricao) LIKE :texto )";


        Query query = getEntityManager().createQuery(sql + " ORDER BY produto.slugDescricao");
        query.setParameter("texto", "%" + StringUtils.removeCaracteresEspeciais(texto).toLowerCase().trim() + "%");
        query.setParameter("aditivo1", UtilizacaoAditivoProduto.EH_UM_ADITIVO);
        query.setParameter("aditivo2", UtilizacaoAditivoProduto.EH_UM_ADITIVO_RETORNAVEL);

        return query.getResultList();
    }

    @Override
    public void save(Produto bean) {
        bean.setSlugDescricao(StringUtils.removeCaracteresEspeciais(bean.getDescricao()));
        super.save(bean);
    }

    @Override
    public Produto update(Produto bean) {
        bean.setSlugDescricao(StringUtils.removeCaracteresEspeciais(bean.getDescricao()));
        return super.update(bean);
    }

    @Override
    public List<Produto> pesquisar(Produto entidade) {
        String sql = "SELECT produto FROM Produto produto WHERE produto.active = TRUE";
        Map<String, Object> params = new HashMap<String, Object>();
        if (!Utils.isEmpty(entidade.getCodigo())) {
            sql += " AND lower(produto.codigo) like :codigo";
            params.put("codigo", "%" + entidade.getCodigo().toLowerCase() + "%");
        }
        if (!Utils.isEmpty(entidade.getDescricao())) {
            sql += " AND lower(produto.slugDescricao) like :descricao";
            params.put("descricao", "%" + StringUtils.removeCaracteresEspeciais(entidade.getDescricao().toLowerCase()) + "%");
        }

        if (entidade.getValor() != null) {
            sql += " AND produto.valor = :valor";
            params.put("valor", entidade.getValor());
        }

        sql += " AND produto.disponivel = :disponivel";
        params.put("disponivel", entidade.isDisponivel());

        Query query = getEntityManager().createQuery(sql + " ORDER BY produto.slugDescricao");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            query.setParameter(key, value);
        }
        return query.getResultList();
    }
}
