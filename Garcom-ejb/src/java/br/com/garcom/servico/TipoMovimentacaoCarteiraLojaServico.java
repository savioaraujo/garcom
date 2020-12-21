/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.enums.Operator;
import br.com.foxinline.util.Param;
import br.com.foxinline.util.StringUtils;
import br.com.foxinline.util.Utils;
import br.com.garcom.enums.TipoMovimentacaoCarteira;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.financeiro.modelo.TipoMovimentacaoCarteiraLoja;
import br.com.garcom.utils.Caracteres;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class TipoMovimentacaoCarteiraLojaServico extends ServicoGenerico<TipoMovimentacaoCarteiraLoja> {

    @Override
    public void save(TipoMovimentacaoCarteiraLoja bean) {
        bean.setSlugDescricao(StringUtils.removeCaracteresEspeciais(bean.getDescricao()));
        super.save(bean);
    }

    @Override
    public TipoMovimentacaoCarteiraLoja update(TipoMovimentacaoCarteiraLoja bean) {
        bean.setSlugDescricao(StringUtils.removeCaracteresEspeciais(bean.getDescricao()));
        return super.update(bean);
    }

    public List<TipoMovimentacaoCarteiraLoja> autocompletar(String query) {
        if (!Utils.isEmpty(query)) {

            return findFilter("slugDecricao", new Param("%" + query + "%", Operator.LIKE_IGNORE_CASE), "ORDER BY", "slugDescricao");
        } else {
            return findFilter("ORDER BY", "slugDescricao");
        }
    }

    @Override
    public List<TipoMovimentacaoCarteiraLoja> pesquisar(TipoMovimentacaoCarteiraLoja entidade) {
        String descricao = entidade.getDescricao();
        if (!Utils.isEmpty(descricao)) {
            descricao = "%" + Caracteres.removeCaracteresEspeciais(descricao) + "%";
        }
        return findFilter("tipo", entidade.getTipo(), "slugDescricao", new Param(descricao, Operator.LIKE_IGNORE_CASE));
    }

    public TipoMovimentacaoCarteiraLoja obterMovimentacaoVenda() {
        return findFilter("tipo", TipoMovimentacaoCarteira.VENDA).get(0);
    }
}
