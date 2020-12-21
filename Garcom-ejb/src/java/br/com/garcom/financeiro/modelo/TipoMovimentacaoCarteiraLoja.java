/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.financeiro.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import br.com.garcom.enums.TipoMovimentacaoCarteira;
import javax.persistence.*;

/**
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "tipo_movimentacao_carteira_loja")
public class TipoMovimentacaoCarteiraLoja extends BasicLoggedBean {

    @Column(columnDefinition = "text")
    private String descricao;
    @Column(columnDefinition = "text", name = "slug_descricao")
    private String slugDescricao;
    @Version
    private int version;
    @Enumerated(EnumType.STRING)
    private TipoMovimentacaoCarteira tipo;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSlugDescricao() {
        return slugDescricao;
    }

    public void setSlugDescricao(String slugDescricao) {
        this.slugDescricao = slugDescricao;
    }

    public TipoMovimentacaoCarteira getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacaoCarteira tipo) {
        this.tipo = tipo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
