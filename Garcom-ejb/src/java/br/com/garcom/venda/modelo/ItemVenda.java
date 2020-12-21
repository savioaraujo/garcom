/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.venda.modelo;

import br.com.garcom.estoque.modelo.Produto;
import br.com.foxinline.annotation.NotAudited;
import br.com.foxinline.model.BasicLoggedBean;
import br.com.foxinline.util.Utils;
import br.com.garcom.usuario.modelo.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "item_venda")
public class ItemVenda extends BasicLoggedBean implements Comparable<ItemVenda> {

    @NotAudited
    @ManyToOne
    private Venda venda;
    @NotAudited
    @ManyToOne
    @JoinColumn(name = "venda_origem_id")
    private Venda vendaOrigem;
    @ManyToOne
    private Produto produto;
    @Column(name = "descricao_produto", columnDefinition = "text")
    private String descricaoProduto;
    private Integer quantidade;
    @Column(precision = 6, scale = 2, name = "valor_unitario")
    private BigDecimal valorUnitario;
    @Column(precision = 6, scale = 2, name = "valor_unitario_ogirinal")
    private BigDecimal valorUnitarioOriginal;
    @Column(precision = 14, scale = 2, name = "valor_total")
    private BigDecimal valorTotal;
    @Column(precision = 14, scale = 2, name = "valor_total_com_aditivo")
    private BigDecimal valorTotalComAditivo;
    @ManyToOne
    @JoinColumn(name = "usuario_cadastro_id")
    private Usuario usuarioCadastro;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "data_adicionado")
    private Date dataAdicionado;
    @ManyToOne
    @JoinColumn(name = "usuario_remocao_id")
    private Usuario usuarioRemocao;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "data_remocao")
    private Date dataRemocao;
    @NotAudited
    @ManyToOne
    @JoinColumn(name = "venda_origem_remocao_id")
    private Venda vendaOrigemRemocao;
    @Column(columnDefinition = "text")
    private String observacoes;
    @Version
    private int version;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "item_venda_item_aditivo", joinColumns =
    @JoinColumn(name = "item_venda_id"), inverseJoinColumns =
    @JoinColumn(name = "item_aditivo_venda_id"))
    private List<ItemAditivoVenda> itensAditivoVenda;

    public ItemVenda() {

        this.quantidade = 1;
        this.valorUnitario = BigDecimal.ZERO;
        this.valorTotal = BigDecimal.ZERO;
    }

    public Date getDataAdicionado() {
        return dataAdicionado;
    }

    public void setDataAdicionado(Date dataAdicionado) {
        this.dataAdicionado = dataAdicionado;
    }

    public Date getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(Date dataRemocao) {
        this.dataRemocao = dataRemocao;
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

    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    public Usuario getUsuarioRemocao() {
        return usuarioRemocao;
    }

    public void setUsuarioRemocao(Usuario usuarioRemocao) {
        this.usuarioRemocao = usuarioRemocao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorUnitarioOriginal() {
        return valorUnitarioOriginal;
    }

    public void setValorUnitarioOriginal(BigDecimal valorUnitarioOriginal) {
        this.valorUnitarioOriginal = valorUnitarioOriginal;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public Venda getVendaOrigemRemocao() {
        return vendaOrigemRemocao;
    }

    public void setVendaOrigemRemocao(Venda vendaOrigemRemocao) {
        this.vendaOrigemRemocao = vendaOrigemRemocao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Venda getVendaOrigem() {
        return vendaOrigem;
    }

    public void setVendaOrigem(Venda vendaOrigem) {
        this.vendaOrigem = vendaOrigem;
    }

    public List<ItemAditivoVenda> getItensAditivoVenda() {
        if (Utils.isEmpty(this.itensAditivoVenda)) {
            this.itensAditivoVenda = new ArrayList<ItemAditivoVenda>();
        }
        return itensAditivoVenda;
    }

    public void setItensAditivoVenda(List<ItemAditivoVenda> itensAditivoVenda) {
        this.itensAditivoVenda = itensAditivoVenda;
    }

    public BigDecimal getValorTotalComAditivo() {
        return valorTotalComAditivo;
    }

    public void setValorTotalComAditivo(BigDecimal valorTotalComAditivo) {
        this.valorTotalComAditivo = valorTotalComAditivo;
    }

    @Override
    public int compareTo(ItemVenda o) {
        int compareTo = o.getId().compareTo(this.getId());
        return compareTo != 0 ? compareTo * -1 : 0;
    }
}
