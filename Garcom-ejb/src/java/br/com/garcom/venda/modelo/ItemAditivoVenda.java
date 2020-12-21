/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.venda.modelo;

import br.com.foxinline.model.BasicBean;
import br.com.garcom.estoque.modelo.ItemAditivoProduto;
import br.com.garcom.estoque.modelo.Produto;
import br.com.garcom.usuario.modelo.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "item_aditivo_venda")
public class ItemAditivoVenda extends BasicBean {

    @ManyToOne
    private Produto produto;
    private Integer quantidade;
    private boolean retornavel;
    @Column(name = "quantidade_devolvido")
    private Integer quantidadeDevolvida;
    @Column(name = "valor_unitario", precision = 6, scale = 2)
    private BigDecimal valorUnitario;
    @Column(name = "valor_total", precision = 6, scale = 2)
    private BigDecimal valorTotal;
    @Column(name = "valor_a_ser_pago", precision = 6, scale = 2)
    private BigDecimal valorASerPago;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_vencimentto")
    private Date dataVencimento;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_recebimento")
    private Date dataRecebimento;
    @ManyToOne
    @JoinColumn(name = "usuario_recebimento_id")
    private Usuario usuarioRecebimento;
    @Transient
    private ItemAditivoProduto itemAditivoProduto;

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

    public boolean isRetornavel() {
        return retornavel;
    }

    public void setRetornavel(boolean retornavel) {
        this.retornavel = retornavel;
    }

    public Integer getQuantidadeDevolvida() {
        return quantidadeDevolvida;
    }

    public void setQuantidadeDevolvida(Integer quantidadeDevolvida) {
        this.quantidadeDevolvida = quantidadeDevolvida;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorASerPago() {
        return valorASerPago;
    }

    public void setValorASerPago(BigDecimal valorASerPago) {
        this.valorASerPago = valorASerPago;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Usuario getUsuarioRecebimento() {
        return usuarioRecebimento;
    }

    public void setUsuarioRecebimento(Usuario usuarioRecebimento) {
        this.usuarioRecebimento = usuarioRecebimento;
    }

    public ItemAditivoProduto getItemAditivoProduto() {
        return itemAditivoProduto;
    }

    public void setItemAditivoProduto(ItemAditivoProduto itemAditivoProduto) {
        this.itemAditivoProduto = itemAditivoProduto;
    }
}
