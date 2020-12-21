/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.estoque.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import br.com.foxinline.util.Utils;
import br.com.garcom.estoque.enums.UtilizacaoAditivoProduto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 *
 * @author evaldosavio
 */
@Entity
public class Produto extends BasicLoggedBean {

    private String descricao;
    @Column(name = "slug_descricao")
    private String slugDescricao;
    private String codigo;
    @Column(precision = 6, scale = 2)
    private BigDecimal valor;
    @Column(precision = 6, scale = 2, name = "valor_minimo")
    private BigDecimal valorMinimo;
    private boolean disponivel;
    private Long estoque;
    @Column(name = "estoque_minimo")
    private Long estoqueMinimo;
    @Column(name = "permitir_escrever_observacoes")
    private boolean permitirEscreverObservacoes;
    @Version
    private int version;
    @Enumerated(EnumType.STRING)
    @Column(name = "utilizacao_aditivo")
    private UtilizacaoAditivoProduto utilizacaoAditivo;
    @Column(name = "pagamento_aditivo_no_ato_da_venda")
    private boolean pagamentoNoAtoDaVenda;
    @Column(name = "prazo_devolucao")
    private Integer prazoDevolucao;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "produto_aditivos", joinColumns =
    @JoinColumn(name = "produto_id"), inverseJoinColumns =
    @JoinColumn(name = "item_aditivo_produto_id"))
    private List<ItemAditivoProduto> itensAditivoProduto;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getSlugDescricao() {
        return slugDescricao;
    }

    public void setSlugDescricao(String slugDescricao) {
        this.slugDescricao = slugDescricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getEstoque() {
        return estoque;
    }

    public void setEstoque(Long estoque) {
        this.estoque = estoque;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isPermitirEscreverObservacoes() {
        return permitirEscreverObservacoes;
    }

    public void setPermitirEscreverObservacoes(boolean permitirEscreverObservacoes) {
        this.permitirEscreverObservacoes = permitirEscreverObservacoes;
    }

    public BigDecimal getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public Long getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Long estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public UtilizacaoAditivoProduto getUtilizacaoAditivo() {
        return utilizacaoAditivo;
    }

    public void setUtilizacaoAditivo(UtilizacaoAditivoProduto utilizacaoAditivo) {
        this.utilizacaoAditivo = utilizacaoAditivo;
    }

    public boolean isPagamentoNoAtoDaVenda() {
        return pagamentoNoAtoDaVenda;
    }

    public void setPagamentoNoAtoDaVenda(boolean pagamentoNoAtoDaVenda) {
        this.pagamentoNoAtoDaVenda = pagamentoNoAtoDaVenda;
    }

    public Integer getPrazoDevolucao() {
        return prazoDevolucao;
    }

    public void setPrazoDevolucao(Integer prazoDevolucao) {
        this.prazoDevolucao = prazoDevolucao;
    }

    public List<ItemAditivoProduto> getItensAditivoProduto() {
        if (Utils.isEmpty(this.itensAditivoProduto)) {
            this.itensAditivoProduto = new ArrayList<ItemAditivoProduto>();
        }
        return itensAditivoProduto;
    }

    public void setItensAditivoProduto(List<ItemAditivoProduto> itensAditivoProduto) {
        this.itensAditivoProduto = itensAditivoProduto;
    }
}
