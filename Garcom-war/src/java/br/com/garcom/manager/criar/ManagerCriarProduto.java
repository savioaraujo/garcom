/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.criar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.Utils;
import br.com.garcom.estoque.enums.UtilizacaoAditivoProduto;
import br.com.garcom.estoque.modelo.ItemAditivoProduto;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerCriar;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.estoque.modelo.Produto;
import br.com.garcom.servico.ProdutoServico;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.OptimisticLockException;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "PRODUTO", pageRedirect = "index.xhtml")
public class ManagerCriarProduto extends ManagerCriar<Produto> {
    
    @EJB
    private ProdutoServico produtoServico;
    private ItemAditivoProduto itemAditivoProduto;
    
    @Override
    public void instanciar() {
        super.instanciar();
        this.entidade.setDisponivel(true);
        this.itemAditivoProduto = new ItemAditivoProduto();
    }
    
    @Override
    public void delegar() {
        super.delegar();
        if (!Utils.isEmpty(this.entidade.getItensAditivoProduto())) {
            this.itemAditivoProduto = this.entidade.getItensAditivoProduto().get(0);
        } else {
            this.itemAditivoProduto = new ItemAditivoProduto();
        }
    }
    
    public void validarValorMinimo() {
        if (this.entidade.getValor() != null && this.entidade.getValorMinimo() != null) {
            if (this.entidade.getValorMinimo().compareTo(this.entidade.getValor()) > 0) {
                this.entidade.setValorMinimo(this.entidade.getValor());
            }
        } else if (this.entidade.getValor() != null) {
            this.entidade.setValorMinimo(this.entidade.getValor());
        } else if (this.entidade.getValorMinimo() != null) {
            this.entidade.setValor(this.entidade.getValorMinimo());
        }
    }
    
    @Override
    public void salvar() {
        try {
            
            if (this.entidade.getUtilizacaoAditivo() != null) {
                if (this.entidade.getUtilizacaoAditivo().equals(UtilizacaoAditivoProduto.UTILIZA_UM_ADITIVO)) {
                    if (itemAditivoProduto.getId() == null) {
                        this.entidade.getItensAditivoProduto().add(itemAditivoProduto);
                    }
                } else if (this.entidade.getUtilizacaoAditivo().equals(UtilizacaoAditivoProduto.EH_UM_ADITIVO)) {
                    this.entidade.setPrazoDevolucao(null);
                    this.entidade.setPagamentoNoAtoDaVenda(false);
                }
            } else {
                this.entidade.setPrazoDevolucao(null);
                this.entidade.setPagamentoNoAtoDaVenda(false);
                this.entidade.setItensAditivoProduto(null);
            }
            super.salvar();
            
        } catch (Exception e) {
            if (e.getCause() instanceof OptimisticLockException) {
                Mensagem.messagemError("Ocorreu um erro durante a atualizaçao do Produto, por favor recarregue a pagina e tente novamente !");
            }
        }
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
