/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.garcom.estoque.enums.TipoMovimentacaoEstoque;
import br.com.garcom.estoque.exceptions.EstoqueInsuficienteException;
import br.com.garcom.estoque.exceptions.ProdutoIndisponivelException;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.venda.modelo.ItemVenda;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.OptimisticLockException;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class ItemVendaServico extends ServicoGenerico<ItemVenda> {

    @EJB
    ItemMovimentacaoEstoqueServico itemMovimentacaoEstoqueServico;

    @Override
    public List<ItemVenda> pesquisar(ItemVenda entidade) {
        return find(entidade);
    }

    public void salvarEMovimentarEstoque(ItemVenda itemVenda) throws EstoqueInsuficienteException {
        if (itemVenda.getProduto() != null) {
            itemMovimentacaoEstoqueServico.movimentarEstoque(itemVenda.getProduto(), TipoMovimentacaoEstoque.VENDA, itemVenda.getQuantidade(), itemVenda.getVenda());
        }
        super.save(itemVenda);
    }

    @Override
    public void save(ItemVenda bean) {
        super.save(bean);
    }

    @Override
    public void inactivate(ItemVenda itemVenda) {
        super.inactivate(itemVenda);
    }

    public void inativarEMovimentarEstoque(ItemVenda itemVenda) throws EstoqueInsuficienteException {
        if (itemVenda.getProduto() != null) {
            itemMovimentacaoEstoqueServico.movimentarEstoque(itemVenda.getProduto(), TipoMovimentacaoEstoque.CANCELAMENTO_VENDA, itemVenda.getQuantidade(), itemVenda.getVendaOrigemRemocao());
        }
        super.inactivate(itemVenda);
    }
}
