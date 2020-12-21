/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.financeiro.modelo;

import br.com.garcom.financeiro.modelo.ItemMovimentacaoCarteiraLoja;
import br.com.foxinline.model.BasicLoggedBean;
import br.com.foxinline.util.Utils;
import br.com.garcom.basico.modelo.Cliente;
import br.com.garcom.usuario.modelo.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "carteira_loja")
public class CarteiraLoja extends BasicLoggedBean {

    @OneToOne
    private Cliente proprietario;
    @OneToMany(mappedBy = "carteiraLoja", cascade = CascadeType.MERGE)
    private List<ItemMovimentacaoCarteiraLoja> itensMovimentacaoCarteiraLoja;
    @ManyToOne
    @JoinColumn(name = "usuario_cadastro_id")
    private Usuario usuarioCadastro;
    @Column(name = "data_cadastro")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Column(precision = 12, scale = 2, name = "saldo")
    private BigDecimal saldo;
    private boolean bloqueada;
    @Column(columnDefinition = "text", name = "motivo_bloqueio")
    private String motivoBloqueio;
    @Column(precision = 14, scale = 2)
    private BigDecimal limiteDebito;
    @ManyToOne
    @JoinColumn(name = "usuario_bloqueio_id")
    private Usuario usuarioBloqueio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_bloqueio")
    private Date dataBloqueio;
    @ManyToOne
    @JoinColumn(name = "usuario_desbloqueio_id")
    private Usuario usuarioDesbloqueio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_debloqueio")
    private Date dataDesbloqueio;
    @Version
    private long version;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_ultima_movimentacao_negativa")
    private Date dataUltimaMovimentacaoNegativa;

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }

    public Date getDataBloqueio() {
        return dataBloqueio;
    }

    public void setDataBloqueio(Date dataBloqueio) {
        this.dataBloqueio = dataBloqueio;
    }

    public Date getDataDesbloqueio() {
        return dataDesbloqueio;
    }

    public void setDataDesbloqueio(Date dataDesbloqueio) {
        this.dataDesbloqueio = dataDesbloqueio;
    }

    public BigDecimal getLimiteDebito() {
        return limiteDebito;
    }

    public void setLimiteDebito(BigDecimal limiteDebito) {
        this.limiteDebito = limiteDebito;
    }

    public String getMotivoBloqueio() {
        return motivoBloqueio;
    }

    public void setMotivoBloqueio(String motivoBloqueio) {
        this.motivoBloqueio = motivoBloqueio;
    }

    public Usuario getUsuarioBloqueio() {
        return usuarioBloqueio;
    }

    public void setUsuarioBloqueio(Usuario usuarioBloqueio) {
        this.usuarioBloqueio = usuarioBloqueio;
    }

    public Usuario getUsuarioDesbloqueio() {
        return usuarioDesbloqueio;
    }

    public void setUsuarioDesbloqueio(Usuario usuarioDesbloqueio) {
        this.usuarioDesbloqueio = usuarioDesbloqueio;
    }

    public List<ItemMovimentacaoCarteiraLoja> getItensMovimentacaoCarteiraLoja() {
        if (Utils.isEmpty(this.itensMovimentacaoCarteiraLoja)) {
            this.itensMovimentacaoCarteiraLoja = new ArrayList<ItemMovimentacaoCarteiraLoja>();
        }
        return itensMovimentacaoCarteiraLoja;
    }

    public void setItensMovimentacaoCarteiraLoja(List<ItemMovimentacaoCarteiraLoja> itensMovimentacaoCarteiraLoja) {
        this.itensMovimentacaoCarteiraLoja = itensMovimentacaoCarteiraLoja;
    }

    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    public Date getDataUltimaMovimentacaoNegativa() {
        return dataUltimaMovimentacaoNegativa;
    }

    public void setDataUltimaMovimentacaoNegativa(Date dataUltimaMovimentacaoNegativa) {
        this.dataUltimaMovimentacaoNegativa = dataUltimaMovimentacaoNegativa;
    }

    public void inverterLista() {

        Collections.sort(getItensMovimentacaoCarteiraLoja(), new Comparator<ItemMovimentacaoCarteiraLoja>() {

            @Override
            public int compare(ItemMovimentacaoCarteiraLoja o1, ItemMovimentacaoCarteiraLoja o2) {
                int compareTo = o1.getDataCadastro().compareTo(o2.getDataCadastro());
                return compareTo != 0 ? compareTo * -1 : 0;
            }
        });
    }
}
