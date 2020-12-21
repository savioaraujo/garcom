/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.criar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.Utils;
import br.com.garcom.financeiro.modelo.ItemJuros;
import br.com.garcom.financeiro.modelo.MaquinaCartao;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerCriar;
import br.com.garcom.servico.ItemJurosServico;
import br.com.garcom.servico.MaquinaCartaoServico;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "MAQUINA CARTÃO", pageRedirect = "index.xhtml")
public class ManagerCriarMaquinaCartao extends ManagerCriar<MaquinaCartao> {

    @EJB
    private MaquinaCartaoServico maquinaCartaoServico;
    @EJB
    private ItemJurosServico itemJurosServico;
    private ItemJuros itemJuros;
    private ItemJuros itemJurosRemocao;
    private List<ItemJuros> itensRemocao;

    @Override
    protected void init() {
        super.init();
        limparItem();
        this.itensRemocao = new ArrayList<ItemJuros>();
    }

    @Override
    public void salvar() {
        if (!Utils.isEmpty(itensRemocao)) {
            for (ItemJuros itemJuros : itensRemocao) {
                itemJuros.setMaquinaCartao(null);
                itemJuros.setActive(false);
                itemJurosServico.update(itemJuros);
            }
        }
        super.salvar();
    }

    public void limparItem() {
        this.itemJuros = new ItemJuros();
        this.itemJuros.setMaquinaCartao(this.entidade);
    }

    public void adicionarItem() {
        if (!this.entidade.getItensJuros().contains(this.itemJuros)) {
            this.entidade.getItensJuros().add(this.itemJuros);
            limparItem();

        }
    }

    public void removerItem() {
        this.entidade.getItensJuros().remove(this.itemJurosRemocao);
        this.itensRemocao.add(this.itemJurosRemocao);
        this.itemJurosRemocao = null;
    }

    public ItemJuros getItemJuros() {
        return itemJuros;
    }

    public void setItemJuros(ItemJuros itemJuros) {
        this.itemJuros = itemJuros;
    }

    public ItemJuros getItemJurosRemocao() {
        return itemJurosRemocao;
    }

    public void setItemJurosRemocao(ItemJuros itemJurosRemocao) {
        this.itemJurosRemocao = itemJurosRemocao;
    }

    public List<ItemJuros> getItensRemocao() {
        return itensRemocao;
    }

    public void setItensRemocao(List<ItemJuros> itensRemocao) {
        this.itensRemocao = itensRemocao;
    }

    @Override
    protected ServicoGenerico getServico() {
        return maquinaCartaoServico;
    }
}
