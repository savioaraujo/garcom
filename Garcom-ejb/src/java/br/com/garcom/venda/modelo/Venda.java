/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.venda.modelo;

import br.com.foxinline.annotation.NotAudited;
import br.com.garcom.enums.FormaPagamento;
import br.com.foxinline.model.BasicLoggedBean;
import br.com.foxinline.util.Utils;
import br.com.garcom.enums.venda.StatusVenda;
import br.com.garcom.enums.venda.TipoVenda;
import br.com.garcom.basico.modelo.Cliente;
import br.com.garcom.financeiro.modelo.ItemPagamento;
import br.com.garcom.usuario.modelo.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author evaldosavio
 */
@Entity
public class Venda extends BasicLoggedBean {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_venda")
    private TipoVenda tipoVenda;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_venda")
    private StatusVenda statusVenda;
    private Long codigo;
    @ManyToOne
    private Cliente cliente;
    @Column(precision = 6, scale = 2, name = "valor_total")
    private BigDecimal valorTotal;
    @OneToMany(mappedBy = "venda")
    private List<ItemVenda> itemVenda;
    @OneToOne
    @NotAudited
    @JoinColumn(name = "orcamento_origem_id")
    private Venda orcamentoOrigem;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataAbertura;
    @ManyToOne
    @JoinColumn(name = "usuario_abertura_id")
    private Usuario usuarioAbertura;
    @ManyToOne
    @JoinColumn(name = "usuario_fechamento_id")
    private Usuario usuarioFechamento;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "data_fechamento")
    private Date dataFechamento;
    @ManyToOne
    @JoinColumn(name = "usuario_cancelamento_id")
    private Usuario usuarioCancelamento;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "data_cancelamento")
    private Date dataCancelamento;
    private boolean aberta;
    @Enumerated(EnumType.STRING)
    private FormaPagamento pagamento;
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemPagamento> itensPagamento;
    @Version
    private int version;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItemVenda() {
        if (Utils.isEmpty(this.itemVenda)) {
            this.itemVenda = new ArrayList<ItemVenda>();
        }
        return itemVenda;
    }

    public void setItemVenda(List<ItemVenda> itemVenda) {
        this.itemVenda = itemVenda;
    }

    public BigDecimal getValorTotal() {
        if (valorTotal == null) {
            valorTotal = BigDecimal.ZERO;
        }
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public boolean isAberta() {
        return aberta;
    }

    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Usuario getUsuarioAbertura() {
        return usuarioAbertura;
    }

    public void setUsuarioAbertura(Usuario usuarioAbertura) {
        this.usuarioAbertura = usuarioAbertura;
    }

    public Usuario getUsuarioFechamento() {
        return usuarioFechamento;
    }

    public void setUsuarioFechamento(Usuario usuarioFechamento) {
        this.usuarioFechamento = usuarioFechamento;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public FormaPagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(FormaPagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public List<ItemPagamento> getItensPagamento() {
        if (Utils.isEmpty(this.itensPagamento)) {
            this.itensPagamento = new ArrayList<ItemPagamento>();
        }
        return itensPagamento;
    }

    public void setItensPagamento(List<ItemPagamento> itensPagamento) {
        this.itensPagamento = itensPagamento;
    }

    public TipoVenda getTipoVenda() {
        return tipoVenda;
    }

    public void setTipoVenda(TipoVenda tipoVenda) {
        this.tipoVenda = tipoVenda;
    }

    public StatusVenda getStatusVenda() {
        return statusVenda;
    }

    public void setStatusVenda(StatusVenda statusVenda) {
        this.statusVenda = statusVenda;
    }

    public Venda getOrcamentoOrigem() {
        return orcamentoOrigem;
    }

    public void setOrcamentoOrigem(Venda orcamentoOrigem) {
        this.orcamentoOrigem = orcamentoOrigem;
    }

    public Usuario getUsuarioCancelamento() {
        return usuarioCancelamento;
    }

    public void setUsuarioCancelamento(Usuario usuarioCancelamento) {
        this.usuarioCancelamento = usuarioCancelamento;
    }

    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public BigDecimal totalParcelas() {
        BigDecimal totalParcelas = BigDecimal.ZERO;
        for (ItemPagamento itemPagamento : this.getItensPagamento()) {
            totalParcelas = totalParcelas.add(itemPagamento.getValor());
        }

        return totalParcelas;
    }
}
