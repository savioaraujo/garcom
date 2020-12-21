/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.helper.venda;

import br.com.foxinline.util.Utils;
import br.com.garcom.enums.venda.StatusVenda;
import br.com.garcom.enums.venda.TipoVenda;
import br.com.garcom.estoque.enums.TipoMovimentacaoEstoque;
import br.com.garcom.estoque.exceptions.EstoqueInsuficienteException;
import br.com.garcom.financeiro.modelo.ItemPagamento;
import br.com.garcom.manager.criar.ManagerCriarVenda;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.servico.ItemMovimentacaoEstoqueServico;
import br.com.garcom.servico.ItemPagamentoServico;
import br.com.garcom.servico.VendaServico;
import br.com.garcom.usuario.modelo.Usuario;
import br.com.garcom.venda.modelo.ItemAditivoVenda;
import br.com.garcom.venda.modelo.ItemVenda;
import br.com.garcom.venda.modelo.Venda;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evaldosavio
 */
public class VendaHelper {

    public static void cancelarVenda(Venda entidade, Usuario usuario, VendaServico vendaServico, ItemMovimentacaoEstoqueServico itemMovimentacaoEstoqueServico, ItemPagamentoServico itemPagamentoServico) {
        entidade = vendaServico.load(entidade.getId());
        entidade = vendaServico.update(entidade);

        entidade.setAberta(false);
        if (entidade.getDataFechamento() == null) {
            entidade.setDataFechamento(new Date());
        }
        if (entidade.getUsuarioFechamento() == null) {
            entidade.setUsuarioFechamento(usuario);
        }

        entidade.setUsuarioCancelamento(usuario);
        entidade.setDataCancelamento(new Date());
        entidade.setStatusVenda(StatusVenda.CANCELADA);
        vendaServico.update(entidade);
        if (entidade.getTipoVenda().equals(TipoVenda.VENDA)) {
            for (ItemVenda itemVendaCancelado : entidade.getItemVenda()) {
                try {
                    itemMovimentacaoEstoqueServico.movimentarEstoque(itemVendaCancelado.getProduto(), TipoMovimentacaoEstoque.CANCELAMENTO_VENDA, itemVendaCancelado.getQuantidade(), entidade);
                } catch (EstoqueInsuficienteException ex) {
                    Logger.getLogger(ManagerCriarVenda.class.getName()).log(Level.SEVERE, null, ex);
                    Mensagem.messagemInfo("Ocorreu um erro durante a volta do estoque !");
                }

                try {
                    for (ItemAditivoVenda itemAditivoVenda : itemVendaCancelado.getItensAditivoVenda()) {
                        itemMovimentacaoEstoqueServico.movimentarEstoque(itemAditivoVenda.getProduto(), TipoMovimentacaoEstoque.CANCELAMENTO_VENDA, itemAditivoVenda.getQuantidade(), entidade);
                    }
                } catch (EstoqueInsuficienteException ex) {
                    Logger.getLogger(ManagerCriarVenda.class.getName()).log(Level.SEVERE, null, ex);
                    Mensagem.messagemInfo("Ocorreu um erro durante a remoçao do item !");
                }
            }
        }

        if (!Utils.isEmpty(entidade.getItensPagamento())) {
            for (ItemPagamento itemPagamento : entidade.getItensPagamento()) {
                itemPagamento.setActive(false);
                itemPagamento.setVenda(null);
                itemPagamento.setDataRemocao(new Date());
                itemPagamento.setUsuarioRemocao(usuario);
                itemPagamento.setVendaOrigem(entidade);
                itemPagamentoServico.update(itemPagamento);
            }
        }

        Mensagem.messagemInfoRedirect("Venda cancelada com sucesso !", "frenteCaixa.xhtml?tipo=" + entidade.getTipoVenda().name());
    }
}
