/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager;

import br.com.foxinline.util.Utils;
import br.com.garcom.enums.FormaPagamento;
import br.com.garcom.enums.RelatorioVenda;
import br.com.garcom.enums.TipoCliente;
import br.com.garcom.enums.TipoEndereco;
import br.com.garcom.enums.TipoInput;
import br.com.garcom.enums.TipoLogradouro;
import br.com.garcom.enums.TipoMovimentacaoCarteira;
import br.com.garcom.enums.TipoTelefone;
import br.com.garcom.enums.venda.TipoVenda;
import br.com.garcom.basico.modelo.Cliente;
import br.com.garcom.estoque.enums.UtilizacaoAditivoProduto;
import br.com.garcom.servico.CarteiraLojaServico;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author foxinline
 */
@ManagedBean(name = "enums")
@ViewScoped
public class ManagerEnums {

    @EJB
    CarteiraLojaServico carteiraLojaServico;

    public List<SelectItem> formasDePagamento() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (FormaPagamento formaPagamento : FormaPagamento.values()) {
            itens.add(new SelectItem(formaPagamento, formaPagamento.getNome()));
        }
        return itens;
    }

    public List<SelectItem> tipoMovimentacaos() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        itens.add(new SelectItem(TipoMovimentacaoCarteira.CREDITO, TipoMovimentacaoCarteira.CREDITO.getNome()));
        itens.add(new SelectItem(TipoMovimentacaoCarteira.DEBITO, TipoMovimentacaoCarteira.DEBITO.getNome()));
        return itens;
    }

    public List<SelectItem> formasDePagamento(Cliente cliente) {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        itens.add(new SelectItem(FormaPagamento.ESPECIE, FormaPagamento.ESPECIE.getNome()));
        itens.add(new SelectItem(FormaPagamento.CARTAO_DEBITO, FormaPagamento.CARTAO_DEBITO.getNome()));
        itens.add(new SelectItem(FormaPagamento.CARTAO_CREDITO, FormaPagamento.CARTAO_CREDITO.getNome()));
        if (cliente != null && !Utils.isEmpty(carteiraLojaServico.buscarCarteiraPorCliente(cliente))) {
            itens.add(new SelectItem(FormaPagamento.CREDITO_LOJA, FormaPagamento.CREDITO_LOJA.getNome()));
        }
        if (cliente != null && cliente.isHabilitaPagamentoFuncionario()) {
            itens.add(new SelectItem(FormaPagamento.USO_INTERNO, FormaPagamento.USO_INTERNO.getNome()));
        }
        return itens;
    }

    public List<SelectItem> tipoTelefone() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (TipoTelefone tipo : TipoTelefone.values()) {
            itens.add(new SelectItem(tipo, tipo.getDescricao()));
        }
        return itens;
    }

    public List<SelectItem> relatoriosVenda() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (RelatorioVenda tipo : RelatorioVenda.values()) {
            itens.add(new SelectItem(tipo, tipo.getNome()));
        }
        return itens;
    }

    public List<SelectItem> tipoCadastro() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (TipoCliente tipoCadastro : TipoCliente.values()) {
            itens.add(new SelectItem(tipoCadastro, tipoCadastro.getDescricao()));
        }
        return itens;
    }

    public List<SelectItem> tipoInput() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (TipoInput tipo : TipoInput.values()) {
            itens.add(new SelectItem(tipo, tipo.getNome()));
        }
        return itens;
    }

    public List<SelectItem> tipoLogradouro() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (TipoLogradouro tipo : TipoLogradouro.values()) {
            itens.add(new SelectItem(tipo, tipo.getNome()));
        }
        return itens;
    }

    public List<SelectItem> tipoEndereco() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (TipoEndereco tipo : TipoEndereco.values()) {
            itens.add(new SelectItem(tipo, tipo.getNome()));
        }
        return itens;
    }

    public List<SelectItem> tipoVenda() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (TipoVenda tipo : TipoVenda.values()) {
            itens.add(new SelectItem(tipo, tipo.getDescricao()));
        }
        return itens;
    }

    public List<SelectItem> utilizacaoAditivo() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (UtilizacaoAditivoProduto tipo : UtilizacaoAditivoProduto.values()) {
            itens.add(new SelectItem(tipo, tipo.getDescricao()));
        }
        return itens;
    }
}
