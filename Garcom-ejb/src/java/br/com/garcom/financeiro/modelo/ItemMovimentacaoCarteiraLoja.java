/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.financeiro.modelo;

import br.com.garcom.venda.modelo.Venda;
import br.com.foxinline.annotation.NotAudited;
import br.com.foxinline.model.BasicLoggedBean;
import br.com.garcom.usuario.modelo.Usuario;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "item_movimentacao_carteira_loja")
public class ItemMovimentacaoCarteiraLoja extends BasicLoggedBean {

    @ManyToOne
    @JoinColumn(name = "carteira_loja_id")
    @NotAudited
    private CarteiraLoja carteiraLoja;
    @ManyToOne
    @NotAudited
    private Venda venda;
    @ManyToOne
    @JoinColumn(name = "usuario_cadastro_id")
    private Usuario usuarioCadastro;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
    @ManyToOne
    @JoinColumn(name = "tipo_movimentacao_carteira_loja_id")
    private TipoMovimentacaoCarteiraLoja tipoMovimentacaoCarteiraLoja;
    @Column(precision = 14, scale = 2)
    private BigDecimal valor;
    @Column(columnDefinition = "text")
    private String motivo;
    @ManyToOne
    @JoinColumn(name = "usuario_remocao_id")
    private Usuario usuarioRemocao;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_remocao")
    private Date dataRemocao;
    @ManyToOne
    @JoinColumn(name = "carteira_origem_id")
    private CarteiraLoja carteiraOrigem;

    public ItemMovimentacaoCarteiraLoja() {
        valor = BigDecimal.ZERO;
        dataCadastro = new Date();
    }

    public CarteiraLoja getCarteiraLoja() {
        return carteiraLoja;
    }

    public void setCarteiraLoja(CarteiraLoja carteiraLoja) {
        this.carteiraLoja = carteiraLoja;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public TipoMovimentacaoCarteiraLoja getTipoMovimentacaoCarteiraLoja() {
        return tipoMovimentacaoCarteiraLoja;
    }

    public void setTipoMovimentacaoCarteiraLoja(TipoMovimentacaoCarteiraLoja tipoMovimentacaoCarteiraLoja) {
        this.tipoMovimentacaoCarteiraLoja = tipoMovimentacaoCarteiraLoja;
    }

    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
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

    public CarteiraLoja getCarteiraOrigem() {
        return carteiraOrigem;
    }

    public void setCarteiraOrigem(CarteiraLoja carteiraOrigem) {
        this.carteiraOrigem = carteiraOrigem;
    }

    public Date getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(Date dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    public Usuario getUsuarioRemocao() {
        return usuarioRemocao;
    }

    public void setUsuarioRemocao(Usuario usuarioRemocao) {
        this.usuarioRemocao = usuarioRemocao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
