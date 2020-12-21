/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.estoque.modelo.ItemMovimentacaoEstoque;
import br.com.garcom.financeiro.modelo.ItemPagamento;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class ItemPagamentoServico extends ServicoGenerico<ItemPagamento> {

    @Override
    public List<ItemPagamento> pesquisar(ItemPagamento entidade) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
