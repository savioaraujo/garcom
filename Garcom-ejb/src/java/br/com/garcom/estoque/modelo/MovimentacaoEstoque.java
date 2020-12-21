/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.estoque.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import br.com.foxinline.util.Utils;
import br.com.garcom.estoque.enums.TipoMovimentacaoEstoque;
import br.com.garcom.usuario.modelo.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "movimentacao_estoque")
public class MovimentacaoEstoque extends BasicLoggedBean {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
    @ManyToOne
    @JoinColumn(name = "usuario_movimentacao_id")
    private Usuario usuarioMovimentacao;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_movimentacao")
    private Date dataMovimentacao;
    @Column(columnDefinition = "text")
    private String descricao;
    @Column(name = "documento_movimentacao")
    private String documentoMovimentacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao_estoque")
    private TipoMovimentacaoEstoque tipoMovimentacaoEstoque;
    @OneToMany
    @JoinTable(name = "movimentacao_estoque_item_movimentacao", joinColumns =
    @JoinColumn(name = "item_movimentacao_estoque_id"), inverseJoinColumns =
    @JoinColumn(name = "movimentacao_estoque_id"))
    private List<ItemMovimentacaoEstoque> itensMovimentacao;

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Usuario getUsuarioMovimentacao() {
        return usuarioMovimentacao;
    }

    public void setUsuarioMovimentacao(Usuario usuarioMovimentacao) {
        this.usuarioMovimentacao = usuarioMovimentacao;
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

    public String getDocumentoMovimentacao() {
        return documentoMovimentacao;
    }

    public void setDocumentoMovimentacao(String documentoMovimentacao) {
        this.documentoMovimentacao = documentoMovimentacao;
    }

    public List<ItemMovimentacaoEstoque> getItensMovimentacao() {
        if (Utils.isEmpty(this.itensMovimentacao)) {
            this.itensMovimentacao = new ArrayList<>();
        }
        return itensMovimentacao;
    }

    public void setItensMovimentacao(List<ItemMovimentacaoEstoque> itensMovimentacao) {
        this.itensMovimentacao = itensMovimentacao;
    }

    public TipoMovimentacaoEstoque getTipoMovimentacaoEstoque() {
        return tipoMovimentacaoEstoque;
    }

    public void setTipoMovimentacaoEstoque(TipoMovimentacaoEstoque tipoMovimentacaoEstoque) {
        this.tipoMovimentacaoEstoque = tipoMovimentacaoEstoque;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.dataCadastro);
        hash = 97 * hash + Objects.hashCode(this.usuarioMovimentacao);
        hash = 97 * hash + Objects.hashCode(this.dataMovimentacao);
        hash = 97 * hash + Objects.hashCode(this.descricao);
        hash = 97 * hash + Objects.hashCode(this.documentoMovimentacao);
        hash = 97 * hash + (this.tipoMovimentacaoEstoque != null ? this.tipoMovimentacaoEstoque.hashCode() : 0);
        hash = 97 * hash + Objects.hashCode(this.itensMovimentacao);
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
        final MovimentacaoEstoque other = (MovimentacaoEstoque) obj;
        if (!Objects.equals(this.dataCadastro, other.dataCadastro)) {
            return false;
        }
        if (!Objects.equals(this.usuarioMovimentacao, other.usuarioMovimentacao)) {
            return false;
        }
        if (!Objects.equals(this.dataMovimentacao, other.dataMovimentacao)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.documentoMovimentacao, other.documentoMovimentacao)) {
            return false;
        }
        if (this.tipoMovimentacaoEstoque != other.tipoMovimentacaoEstoque) {
            return false;
        }
        if (!Objects.equals(this.itensMovimentacao, other.itensMovimentacao)) {
            return false;
        }
        return true;
    }
}
