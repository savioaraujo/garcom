/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.financeiro.modelo.ItemMovimentacaoCarteiraLoja;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class ItemMovimentacaoCarteiraLojaServico extends ServicoGenerico<ItemMovimentacaoCarteiraLoja> {

    @Override
    public List<ItemMovimentacaoCarteiraLoja> pesquisar(ItemMovimentacaoCarteiraLoja entidade) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
