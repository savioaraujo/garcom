/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.Utils;
import br.com.garcom.estoque.modelo.ItemAditivoProduto;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerPesquisar;
import br.com.garcom.servico.ProdutoServico;
import br.com.garcom.estoque.modelo.Produto;
import br.com.garcom.estoque.modelo.ItemMovimentacaoEstoque;
import br.com.garcom.servico.ItemMovimentacaoEstoqueServico;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "PRODUTO", pageRedirect = "index.xhtml")
public class ManagerPesquisarProduto extends ManagerPesquisar<Produto> {

    @EJB
    private ProdutoServico produtoServico;
    @EJB
    private ItemMovimentacaoEstoqueServico itemMovimentacaoEstoqueServico;
    private List<ItemMovimentacaoEstoque> ultimasMovimentacoes;
    private ItemAditivoProduto itemAditivoProduto;

    @Override
    public void instanciar() {
        super.instanciar();
        this.entidade.setDisponivel(true);
    }

    @Override
    public void delegar() {
        super.delegar();
        this.ultimasMovimentacoes = itemMovimentacaoEstoqueServico.ultimasMovimentacoesProduto(this.entidade);
        if (!Utils.isEmpty(this.entidade.getItensAditivoProduto())) {
            itemAditivoProduto = this.entidade.getItensAditivoProduto().get(0);
        } else {
            itemAditivoProduto = new ItemAditivoProduto();
        }
    }

    public List<ItemMovimentacaoEstoque> getUltimasMovimentacoes() {
        return ultimasMovimentacoes;
    }

    public void setUltimasMovimentacoes(List<ItemMovimentacaoEstoque> ultimasMovimentacoes) {
        this.ultimasMovimentacoes = ultimasMovimentacoes;
    }

    public ItemAditivoProduto getItemAditivoProduto() {
        return itemAditivoProduto;
    }

    public void setItemAditivoProduto(ItemAditivoProduto itemAditivoProduto) {
        this.itemAditivoProduto = itemAditivoProduto;
    }

    @Override
    protected ServicoGenerico getServico() {
        return produtoServico;
    }
}
