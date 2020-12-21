/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.financeiro.modelo;

import br.com.foxinline.annotation.NotAudited;
import br.com.garcom.venda.modelo.Venda;
import br.com.garcom.enums.FormaPagamento;
import br.com.foxinline.model.BasicLoggedBean;
import br.com.garcom.usuario.modelo.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "item_pagamento")
public class ItemPagamento extends BasicLoggedBean {

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;
    @Column(precision = 8, scale = 2, name = "valor")
    private BigDecimal valor;
    @NotAudited
    @ManyToOne
    private Venda venda;
    @NotAudited
    @ManyToOne
    @JoinColumn(name = "venda_origem_id")
    private Venda vendaOrigem;
    @ManyToOne
    @JoinColumn(name = "usuario_remocao_id")
    private Usuario usuarioRemocao;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_remocao")
    private Date dataRemocao;
    @Temporal(TemporalType.DATE)
    @Column(name = "prazo_pagamento")
    private Date prazoPagamento;
    @ManyToOne
    @JoinColumn(name = "maquina_cartao_id")
    private MaquinaCartao maquinaCartao;
    @ManyToOne
    @JoinColumn(name = "bandeira_cartao_id")
    private BandeiraCartao bandeiraCartao;
    @Column(name = "taxa_juros", precision = 6, scale = 4)
    private BigDecimal taxaJuros;
    private Long parcelamento;
    @Transient
    private ItemJuros itemJuros;
    private String descricao;

    public ItemPagamento() {
        this.formaPagamento = FormaPagamento.ESPECIE;
        this.valor = BigDecimal.ZERO;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Date getPrazoPagamento() {
        return prazoPagamento;
    }

    public void setPrazoPagamento(Date prazoPagamento) {
        this.prazoPagamento = prazoPagamento;
    }

    public MaquinaCartao getMaquinaCartao() {
        return maquinaCartao;
    }

    public void setMaquinaCartao(MaquinaCartao maquinaCartao) {
        this.maquinaCartao = maquinaCartao;
    }

    public BandeiraCartao getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(BandeiraCartao bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public Long getParcelamento() {
        return parcelamento;
    }

    public void setParcelamento(Long parcelamento) {
        this.parcelamento = parcelamento;
    }

    public ItemJuros getItemJuros() {
        return itemJuros;
    }

    public void setItemJuros(ItemJuros itemJuros) {
        this.itemJuros = itemJuros;
    }

    public Venda getVendaOrigem() {
        return vendaOrigem;
    }

    public void setVendaOrigem(Venda vendaOrigem) {
        this.vendaOrigem = vendaOrigem;
    }

    public Usuario getUsuarioRemocao() {
        return usuarioRemocao;
    }

    public void setUsuarioRemocao(Usuario usuarioRemocao) {
        this.usuarioRemocao = usuarioRemocao;
    }

    public Date getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(Date dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.formaPagamento != null ? this.formaPagamento.hashCode() : 0);
        hash = 79 * hash + Objects.hashCode(this.valor);
        hash = 79 * hash + Objects.hashCode(this.venda);
        hash = 79 * hash + Objects.hashCode(this.prazoPagamento);
        hash = 79 * hash + Objects.hashCode(this.maquinaCartao);
        hash = 79 * hash + Objects.hashCode(this.bandeiraCartao);
        hash = 79 * hash + Objects.hashCode(this.taxaJuros);
        hash = 79 * hash + Objects.hashCode(this.parcelamento);
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
        final ItemPagamento other = (ItemPagamento) obj;
        if (this.formaPagamento != other.formaPagamento) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.venda, other.venda)) {
            return false;
        }
        if (!Objects.equals(this.prazoPagamento, other.prazoPagamento)) {
            return false;
        }
        if (!Objects.equals(this.maquinaCartao, other.maquinaCartao)) {
            return false;
        }
        if (!Objects.equals(this.bandeiraCartao, other.bandeiraCartao)) {
            return false;
        }
        if (!Objects.equals(this.taxaJuros, other.taxaJuros)) {
            return false;
        }
        if (!Objects.equals(this.parcelamento, other.parcelamento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemPagamento{" + "formaPagamento=" + formaPagamento + ", valor=" + valor + ", venda=" + venda + ", prazoPagamento=" + prazoPagamento + ", maquinaCartao=" + maquinaCartao + ", bandeiraCartao=" + bandeiraCartao + ", taxaJuros=" + taxaJuros + ", parcelamento=" + parcelamento + '}';
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
