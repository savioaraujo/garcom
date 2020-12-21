/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.estoque.modelo;

import br.com.foxinline.annotation.NotAudited;
import br.com.garcom.estoque.enums.TipoMovimentacaoEstoque;
import br.com.foxinline.model.BasicLoggedBean;
import br.com.garcom.venda.modelo.Venda;
import br.com.garcom.usuario.modelo.Usuario;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "item_movimentacao_estoque")
public class ItemMovimentacaoEstoque extends BasicLoggedBean {

    @ManyToOne
    @NotAudited
    private Produto produto;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao")
    private TipoMovimentacaoEstoque tipoMovimentacao;
    private Integer quantidade;
    @Column(columnDefinition = "text")
    private String descricao;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_movimentacao")
    private Date dataMovimentacao;
    @ManyToOne
    @JoinColumn(name = "usuario_movimentacao_id")
    private Usuario usuarioMovimentacao;
    @Column(name = "estoque_anterior")
    private Long estoqueAnterior;
    @Column(name = "estoque_atual")
    private Long estoqueAtual;
    @ManyToOne
    @NotAudited
    private Venda venda;

    public ItemMovimentacaoEstoque() {
    }

    public ItemMovimentacaoEstoque(Produto produto, TipoMovimentacaoEstoque tipoMovimentacao, Integer quantidade, String descricao, Date dataMovimentacao, Usuario usuarioMovimentacao, Long estoqueAnterior) {
        this.produto = produto;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.dataMovimentacao = dataMovimentacao;
        this.usuarioMovimentacao = usuarioMovimentacao;
        this.estoqueAnterior = estoqueAnterior;
    }

    public Date getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(Date dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getEstoqueAnterior() {
        return estoqueAnterior;
    }

    public void setEstoqueAnterior(Long estoqueAnterior) {
        this.estoqueAnterior = estoqueAnterior;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Usuario getUsuarioMovimentacao() {
        return usuarioMovimentacao;
    }

    public void setUsuarioMovimentacao(Usuario usuarioMovimentacao) {
        this.usuarioMovimentacao = usuarioMovimentacao;
    }

    public TipoMovimentacaoEstoque getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacaoEstoque tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public Long getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(Long estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.produto);
        hash = 47 * hash + (this.tipoMovimentacao != null ? this.tipoMovimentacao.hashCode() : 0);
        hash = 47 * hash + Objects.hashCode(this.quantidade);
        hash = 47 * hash + Objects.hashCode(this.descricao);
        hash = 47 * hash + Objects.hashCode(this.dataMovimentacao);
        hash = 47 * hash + Objects.hashCode(this.usuarioMovimentacao);
        hash = 47 * hash + Objects.hashCode(this.estoqueAnterior);
        hash = 47 * hash + Objects.hashCode(this.estoqueAtual);
        hash = 47 * hash + Objects.hashCode(this.venda);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemMovimentacaoEstoque other = (ItemMovimentacaoEstoque) obj;
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        if (this.tipoMovimentacao != other.tipoMovimentacao) {
            return false;
        }
        if (!Objects.equals(this.quantidade, other.quantidade)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.dataMovimentacao, other.dataMovimentacao)) {
            return false;
        }
        if (!Objects.equals(this.usuarioMovimentacao, other.usuarioMovimentacao)) {
            return false;
        }
        if (!Objects.equals(this.estoqueAnterior, other.estoqueAnterior)) {
            return false;
        }
        if (!Objects.equals(this.estoqueAtual, other.estoqueAtual)) {
            return false;
        }
        if (!Objects.equals(this.venda, other.venda)) {
            return false;
        }
        return true;
    }
}
