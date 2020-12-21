/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.financeiro.modelo;

import br.com.foxinline.annotation.NotAudited;
import br.com.foxinline.model.BasicLoggedBean;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "item_juros")
public class ItemJuros extends BasicLoggedBean {

    @ManyToOne
    @JoinColumn(name = "bandeira_id")
    private BandeiraCartao bandeira;
    @Column(precision = 6, scale = 4)
    private BigDecimal taxa;
    private Long parcelamento;
    @ManyToOne
    @NotAudited
    @JoinColumn(name = "maquina_cartao_id")
    private MaquinaCartao maquinaCartao;

    public ItemJuros() {
    }

    public BandeiraCartao getBandeira() {
        return bandeira;
    }

    public void setBandeira(BandeiraCartao bandeira) {
        this.bandeira = bandeira;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public Long getParcelamento() {
        return parcelamento;
    }

    public void setParcelamento(Long parcelamento) {
        this.parcelamento = parcelamento;
    }

    public MaquinaCartao getMaquinaCartao() {
        return maquinaCartao;
    }

    public void setMaquinaCartao(MaquinaCartao maquinaCartao) {
        this.maquinaCartao = maquinaCartao;
    }
}
