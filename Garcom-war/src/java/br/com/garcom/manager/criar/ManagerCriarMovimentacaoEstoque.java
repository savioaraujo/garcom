/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.criar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.Utils;
import br.com.garcom.estoque.enums.TipoMovimentacaoEstoque;
import br.com.garcom.estoque.exceptions.EstoqueInsuficienteException;
import br.com.garcom.estoque.modelo.ItemMovimentacaoEstoque;
import br.com.garcom.estoque.modelo.MovimentacaoEstoque;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerCriar;
import br.com.garcom.servico.ItemMovimentacaoEstoqueServico;
import br.com.garcom.servico.MovimentacaoEstoqueServico;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "ESTOQUE", pageRedirect = "index.xhtml")
public class ManagerCriarMovimentacaoEstoque extends ManagerCriar<MovimentacaoEstoque> {

    @EJB
    private MovimentacaoEstoqueServico movimentacaoEstoqueServico;
    @EJB
    private ItemMovimentacaoEstoqueServico itemMovimentacaoEstoqueServico;
    private ItemMovimentacaoEstoque itemMovimentacaoEstoque;
    private ItemMovimentacaoEstoque itemMovimentacaoEstoqueRemocao;
    private List<ItemMovimentacaoEstoque> itensMovimentacaoEstoqueRemovidos;
    private TipoMovimentacaoEstoque tipoMovimentacaoEstoque;

    @Override
    protected void init() {
        String tipoMovimentacao = getParameter("tipo");
        this.tipoMovimentacaoEstoque = !Utils.isEmpty(tipoMovimentacao) && tipoMovimentacao.equals("SAIDA") ? TipoMovimentacaoEstoque.SAIDA : TipoMovimentacaoEstoque.ENTRADA;
        super.init();
        this.itensMovimentacaoEstoqueRemovidos = new ArrayList<>();
        instanciarItemMovimentacao();
    }

    @Override
    public void instanciar() {
        super.instanciar();
        this.entidade.setTipoMovimentacaoEstoque(this.tipoMovimentacaoEstoque);
    }

    @Override
    public void delegar() {
        super.delegar();
    }

    public void instanciarItemMovimentacao() {
        this.itemMovimentacaoEstoque = new ItemMovimentacaoEstoque();
    }

    public void adicionarItem() {
        if (this.itemMovimentacaoEstoque != null && this.itemMovimentacaoEstoque.getProduto() != null) {
            this.entidade.getItensMovimentacao().add(this.itemMovimentacaoEstoque);
            instanciarItemMovimentacao();
        }
    }

    public void removerItem() {
        if (this.itemMovimentacaoEstoqueRemocao != null) {
            this.entidade.getItensMovimentacao().remove(this.itemMovimentacaoEstoqueRemocao);
            if (this.itemMovimentacaoEstoqueRemocao.getId() != null) {
                this.itensMovimentacaoEstoqueRemovidos.add(this.itemMovimentacaoEstoqueRemocao);
            }
            this.itemMovimentacaoEstoqueRemocao = null;
        }
    }

    @Override
    public void salvar() {
        List<ItemMovimentacaoEstoque> itensSalvos = new ArrayList<>();
        for (ItemMovimentacaoEstoque item : this.entidade.getItensMovimentacao()) {
            if (item.getId() == null) {
                try {

                    item = itemMovimentacaoEstoqueServico.movimentarEstoque(item.getProduto(), this.entidade.getTipoMovimentacaoEstoque(), item.getQuantidade(), null);
                    item.setDescricao(this.entidade.getTipoMovimentacaoEstoque().getNome().toUpperCase() + " DE ESTOQUE : " + this.entidade.getDescricao());
                    itemMovimentacaoEstoqueServico.update(item);
                    itensSalvos.add(item);
                } catch (EstoqueInsuficienteException ex) {
                    Logger.getLogger(ManagerCriarMovimentacaoEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                itensSalvos.add(item);
            }
        }

        TipoMovimentacaoEstoque tipoCancelamentoItem = this.entidade.getTipoMovimentacaoEstoque().equals(TipoMovimentacaoEstoque.ENTRADA) ? TipoMovimentacaoEstoque.SAIDA : TipoMovimentacaoEstoque.ENTRADA;
        for (ItemMovimentacaoEstoque item : this.itensMovimentacaoEstoqueRemovidos) {
            if (item.getId() != null) {
                try {
                    item = itemMovimentacaoEstoqueServico.movimentarEstoque(item.getProduto(), tipoCancelamentoItem, item.getQuantidade(), null);
                    item.setDescricao("REMOVIDO DA " + this.entidade.getTipoMovimentacaoEstoque().getNome().toUpperCase() + " DE ESTOQUE");
                    itemMovimentacaoEstoqueServico.update(item);
                } catch (EstoqueInsuficienteException ex) {
                    Logger.getLogger(ManagerCriarMovimentacaoEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        this.entidade.setItensMovimentacao(itensSalvos);
        this.entidade.setDataCadastro(new Date());
        this.entidade.setUsuarioMovimentacao(this.usuarioServico.getUserLogged());
        super.salvar();
    }

    public MovimentacaoEstoqueServico getMovimentacaoEstoqueServico() {
        return movimentacaoEstoqueServico;
    }

    public void setMovimentacaoEstoqueServico(MovimentacaoEstoqueServico movimentacaoEstoqueServico) {
        this.movimentacaoEstoqueServico = movimentacaoEstoqueServico;
    }

    public ItemMovimentacaoEstoque getItemMovimentacaoEstoque() {
        return itemMovimentacaoEstoque;
    }

    public void setItemMovimentacaoEstoque(ItemMovimentacaoEstoque itemMovimentacaoEstoque) {
        this.itemMovimentacaoEstoque = itemMovimentacaoEstoque;
    }

    public ItemMovimentacaoEstoque getItemMovimentacaoEstoqueRemocao() {
        return itemMovimentacaoEstoqueRemocao;
    }

    public void setItemMovimentacaoEstoqueRemocao(ItemMovimentacaoEstoque itemMovimentacaoEstoqueRemocao) {
        this.itemMovimentacaoEstoqueRemocao = itemMovimentacaoEstoqueRemocao;
    }

    public List<ItemMovimentacaoEstoque> getItensMovimentacaoEstoqueRemovidos() {
        return itensMovimentacaoEstoqueRemovidos;
    }

    public void setItensMovimentacaoEstoqueRemovidos(List<ItemMovimentacaoEstoque> itensMovimentacaoEstoqueRemovidos) {
        this.itensMovimentacaoEstoqueRemovidos = itensMovimentacaoEstoqueRemovidos;
    }

    public TipoMovimentacaoEstoque getTipoMovimentacaoEstoque() {
        return tipoMovimentacaoEstoque;
    }

    public void setTipoMovimentacaoEstoque(TipoMovimentacaoEstoque tipoMovimentacaoEstoque) {
        this.tipoMovimentacaoEstoque = tipoMovimentacaoEstoque;
    }

    @Override
    protected ServicoGenerico getServico() {
        return movimentacaoEstoqueServico;
    }
}
