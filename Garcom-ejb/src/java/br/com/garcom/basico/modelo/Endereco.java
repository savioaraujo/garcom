/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.basico.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import br.com.garcom.enums.TipoEndereco;
import br.com.garcom.enums.TipoLogradouro;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

/**
 *
 * @author evaldosavio
 */
@Entity
public class Endereco extends BasicLoggedBean {

    @Enumerated(EnumType.STRING)
    private TipoEndereco tipo;
    @Column(name = "complemento_tipo")
    private String complementoTipo;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_logradouro")
    private TipoLogradouro tipoLogradouro;
    private String logradouro;
    @Column(length = 10)
    private String numero;
    @OneToOne
    private Bairro bairro;
    @OneToOne
    private Cidade cidade;
    @Column(length = 8)
    private String cep;
    @Column(length = 100)
    private String localidade;
    @Column(columnDefinition = "text")
    private String complemento;

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getComplementoTipo() {
        return complementoTipo;
    }

    public void setComplementoTipo(String complementoTipo) {
        this.complementoTipo = complementoTipo;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoEndereco getTipo() {
        return tipo;
    }

    public void setTipo(TipoEndereco tipo) {
        this.tipo = tipo;
    }

    public TipoLogradouro getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }
}
