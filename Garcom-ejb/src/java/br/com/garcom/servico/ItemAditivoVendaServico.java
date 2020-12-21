/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.garcom.main.ServicoGenericoBasico;
import br.com.garcom.venda.modelo.ItemAditivoVenda;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class ItemAditivoVendaServico extends ServicoGenericoBasico<ItemAditivoVenda> {

    @Override
    public List<ItemAditivoVenda> pesquisar(ItemAditivoVenda entidade) {
        return this.find(entidade);
    }
}
