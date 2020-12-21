/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.basico.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import br.com.foxinline.util.Utils;
import br.com.garcom.enums.TipoCliente;
import br.com.garcom.validador.CPF;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author foxinline
 */
@Entity
public class Cliente extends BasicLoggedBean {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoCliente tipo;
    private String nome;
    private String apelido;
    @Column(name = "slug_nome")
    private String slugNome;
    @Column(name = "slug_apelido")
    private String slugApelido;
    @OneToMany(cascade= CascadeType.ALL)
    @JoinTable(name = "cliente_documentos", inverseJoinColumns =
    @JoinColumn(name = "documento_id"))
    private List<Documento> documentos;
    private String cpf;
    private String dci;
    private String cgnid;
    @Temporal(TemporalType.DATE)
    @Column(name = "dataNascimento")
    private Date dataNascimento;
    @Column(name = "habilita_pagamento_funcionario")
    private boolean habilitaPagamentoFuncionario;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cliente_telefones", inverseJoinColumns =
    @JoinColumn(name = "telefone_id"))
    private List<Telefone> telefones;
    @ManyToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getCgnid() {
        return cgnid;
    }

    public void setCgnid(String cgnid) {
        this.cgnid = cgnid;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDci() {
        return dci;
    }

    public void setDci(String dci) {
        this.dci = dci;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isHabilitaPagamentoFuncionario() {
        return habilitaPagamentoFuncionario;
    }

    public void setHabilitaPagamentoFuncionario(boolean habilitaPagamentoFuncionario) {
        this.habilitaPagamentoFuncionario = habilitaPagamentoFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSlugApelido() {
        return slugApelido;
    }

    public void setSlugApelido(String slugApelido) {
        this.slugApelido = slugApelido;
    }

    public String getSlugNome() {
        return slugNome;
    }

    public void setSlugNome(String slugNome) {
        this.slugNome = slugNome;
    }

    public List<Telefone> getTelefones() {
        if (Utils.isEmpty(this.telefones)) {
            this.telefones = new ArrayList<Telefone>();
        }
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Documento> getDocumentos() {
        if(Utils.isEmpty(this.documentos)){
            this.documentos = new ArrayList<Documento>();
        }
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }
}
