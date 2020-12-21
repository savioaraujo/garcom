/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.util.DateUtils;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.financeiro.modelo.CarteiraLoja;
import br.com.garcom.basico.modelo.Cliente;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class CarteiraLojaServico extends ServicoGenerico<CarteiraLoja> {

    public List<CarteiraLoja> autocompletar(String texto) {
        return findAll();
    }

    public List<CarteiraLoja> buscarCarteiraPorCliente(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        String sql = "SELECT carteira FROM CarteiraLoja carteira"
                + " INNER JOIN carteira.proprietario proprietario"
                + " LEFT JOIN carteira.itensMovimentacaoCarteiraLoja itens"
                + " WHERE proprietario = :cliente AND carteira.active = TRUE"
                + " ORDER BY itens.dataCadastro DESC";
        Query query = getEntityManager().createQuery(sql);
        query.setMaxResults(100);
        query.setParameter("cliente", cliente);
        return query.getResultList();
    }

    public List<CarteiraLoja> buscarClientesInadimplentes() {
        String sql = "SELECT carteira FROM CarteiraLoja carteira"
                + " WHERE   carteira.active = TRUE AND "
                + "         carteira.saldo < 0 AND "
                + "         carteira.dataUltimaMovimentacaoNegativa <= :data30dias";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter("data30dias", DateUtils.alterDate(new Date(), -30));
        return query.getResultList();
    }

    @Override
    public List<CarteiraLoja> pesquisar(CarteiraLoja entidade) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
