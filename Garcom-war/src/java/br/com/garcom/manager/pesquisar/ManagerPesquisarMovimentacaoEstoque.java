/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.garcom.estoque.enums.TipoMovimentacaoEstoque;
import br.com.garcom.estoque.exceptions.EstoqueInsuficienteException;
import br.com.garcom.estoque.modelo.ItemMovimentacaoEstoque;
import br.com.garcom.estoque.modelo.MovimentacaoEstoque;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerPesquisar;
import br.com.garcom.manager.criar.ManagerCriarMovimentacaoEstoque;
import br.com.garcom.servico.ItemMovimentacaoEstoqueServico;
import br.com.garcom.servico.MovimentacaoEstoqueServico;
import java.util.Date;
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
public class ManagerPesquisarMovimentacaoEstoque extends ManagerPesquisar<MovimentacaoEstoque> {

    @EJB
    private MovimentacaoEstoqueServico movimentacaoEstoqueServico;
    @EJB
    private ItemMovimentacaoEstoqueServico itemMovimentacaoEstoqueServico;
    private Date dataInicio;
    private Date dataFinal;

    @Override
    public boolean delete() {
        TipoMovimentacaoEstoque tipoCancelamentoItem = this.entidade.getTipoMovimentacaoEstoque().equals(TipoMovimentacaoEstoque.ENTRADA) ? TipoMovimentacaoEstoque.SAIDA : TipoMovimentacaoEstoque.ENTRADA;
        for (ItemMovimentacaoEstoque item : this.entidade.getItensMovimentacao()) {
            if (item.getId() != null) {
                try {
                    item = itemMovimentacaoEstoqueServico.movimentarEstoque(item.getProduto(), tipoCancelamentoItem, item.getQuantidade(), null);
                    item.setDescricao(tipoCancelamentoItem.getNome().toUpperCase() + " DE ESTOQUE EXCLUÍDA");
                    itemMovimentacaoEstoqueServico.update(item);
                } catch (EstoqueInsuficienteException ex) {
                    Logger.getLogger(ManagerCriarMovimentacaoEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return super.delete();
    }

    public void validarPeriodo() {
        if (this.dataInicio == null) {
            this.dataFinal = null;
        } else if (dataFinal == null) {
            this.dataFinal = this.dataInicio;
        }
    }

    @Override
    public void pesquisar() {
        this.resultados = movimentacaoEstoqueServico.pesquisar(this.entidade, this.dataInicio, this.dataFinal);
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    @Override
    protected ServicoGenerico getServico() {
        return movimentacaoEstoqueServico;
    }
}
