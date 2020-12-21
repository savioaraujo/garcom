/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.criar;

import br.com.garcom.basico.modelo.Cliente;
import br.com.garcom.financeiro.modelo.CarteiraLoja;
import br.com.garcom.financeiro.modelo.ItemMovimentacaoCarteiraLoja;
import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.Utils;
import br.com.garcom.enums.TipoMovimentacaoCarteira;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerCriar;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.servico.CarteiraLojaServico;
import br.com.garcom.servico.ClienteServico;
import br.com.garcom.servico.ItemMovimentacaoCarteiraLojaServico;
import br.com.garcom.usuario.modelo.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
@AccessPermission(moduleName = "CARTEIRA", pageRedirect = "index.xhtml")
public class ManagerCriarCarteiraLoja extends ManagerCriar<CarteiraLoja> {

    @EJB
    private CarteiraLojaServico carteiraLojaServico;
    @EJB
    private ClienteServico clienteServico;
    @EJB
    private ItemMovimentacaoCarteiraLojaServico itemMovimentacaoCarteiraLojaServico;
    private ItemMovimentacaoCarteiraLoja itemMovimentacaoCarteiraLoja;
    private ItemMovimentacaoCarteiraLoja itemMovimentacaoCarteiraLojaRemocao;
    private Cliente clienteDialog;
    private Usuario usuarioLogado;
    private boolean adicionando;

    @Override
    protected void init() {
        super.init();
        instanciarSubEntidades();
    }

    @Override
    public void instanciar() {
        super.instanciar();
        this.entidade.setSaldo(BigDecimal.ZERO);
    }

    @Override
    public void delegar() {
        super.delegar();
    }

    private void instanciarSubEntidades() {
        this.itemMovimentacaoCarteiraLoja = new ItemMovimentacaoCarteiraLoja();
        this.clienteDialog = new Cliente();
    }

    public void buscarCarteira() {
        List<CarteiraLoja> result = carteiraLojaServico.buscarCarteiraPorCliente(this.entidade.getProprietario());
        if (!Utils.isEmpty(result)) {
            this.entidade = result.get(0);
            Mensagem.messagemInfo("Carteira localizada.");
        } else {
            Cliente cliente = this.entidade.getProprietario();
            this.entidade = new CarteiraLoja();
            this.entidade.setProprietario(cliente);
        }
        this.entidade.inverterLista();

    }

    public void salvarCliente() {
        if (this.clienteDialog.getId() == null) {
            clienteServico.save(this.clienteDialog);
            this.entidade = new CarteiraLoja();
            this.entidade.setSaldo(BigDecimal.ZERO);
        } else {
            clienteServico.update(clienteDialog);
        }
        this.entidade.setProprietario(clienteDialog);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("clienteDialog.hide();");
    }

    public void adicionarItem() {
        if (!adicionando) {
            this.adicionando = true;

            this.itemMovimentacaoCarteiraLoja.setDataCadastro(new Date());
            this.itemMovimentacaoCarteiraLoja.setUsuarioCadastro(usuarioLogado);
            if (this.entidade.getId() == null) {
                List<CarteiraLoja> carteira = carteiraLojaServico.buscarCarteiraPorCliente(this.entidade.getProprietario());
                if (!Utils.isEmpty(carteira)) {
                    this.entidade = carteira.get(0);
                }
                salvarCarteira();
            }
            this.entidade = carteiraLojaServico.load(this.entidade.getId());

            this.itemMovimentacaoCarteiraLoja.setCarteiraLoja(this.entidade);
            this.entidade.getItensMovimentacaoCarteiraLoja().add(itemMovimentacaoCarteiraLoja);
            BigDecimal valorSoma = this.itemMovimentacaoCarteiraLoja.getValor().multiply(this.itemMovimentacaoCarteiraLoja.getTipoMovimentacaoCarteiraLoja().getTipo().equals(TipoMovimentacaoCarteira.CREDITO) ? BigDecimal.ONE : BigDecimal.valueOf(-1L));
            this.entidade.setSaldo(this.entidade.getSaldo().add(valorSoma));
            if (this.entidade.getSaldo().compareTo(BigDecimal.ZERO) >= 0) {
                this.entidade.setDataUltimaMovimentacaoNegativa(null);
            } else if (this.entidade.getDataUltimaMovimentacaoNegativa() == null) {
                this.entidade.setDataUltimaMovimentacaoNegativa(new Date());
            }
            carteiraLojaServico.update(this.entidade);
            instanciarSubEntidades();
            this.entidade.inverterLista();
            this.adicionando = false;
        }
    }

    private void salvarCarteira() {
        this.entidade.setBloqueada(false);
        this.entidade.setDataCadastro(new Date());
        this.entidade.setUsuarioCadastro(usuarioLogado);
        this.entidade.setSaldo(BigDecimal.ZERO);
        carteiraLojaServico.save(this.entidade);
    }

    public CarteiraLojaServico getCarteiraLojaServico() {
        return carteiraLojaServico;
    }

    public void setCarteiraLojaServico(CarteiraLojaServico carteiraLojaServico) {
        this.carteiraLojaServico = carteiraLojaServico;
    }

    public Cliente getClienteDialog() {
        return clienteDialog;
    }

    public void setClienteDialog(Cliente clienteDialog) {
        this.clienteDialog = clienteDialog;
    }

    public ItemMovimentacaoCarteiraLoja getItemMovimentacaoCarteiraLoja() {
        return itemMovimentacaoCarteiraLoja;
    }

    public void setItemMovimentacaoCarteiraLoja(ItemMovimentacaoCarteiraLoja itemMovimentacaoCarteiraLoja) {
        this.itemMovimentacaoCarteiraLoja = itemMovimentacaoCarteiraLoja;
    }

    public ItemMovimentacaoCarteiraLoja getItemMovimentacaoCarteiraLojaRemocao() {
        return itemMovimentacaoCarteiraLojaRemocao;
    }

    public void setItemMovimentacaoCarteiraLojaRemocao(ItemMovimentacaoCarteiraLoja itemMovimentacaoCarteiraLojaRemocao) {
        this.itemMovimentacaoCarteiraLojaRemocao = itemMovimentacaoCarteiraLojaRemocao;
    }

    @Override
    protected ServicoGenerico getServico() {
        return carteiraLojaServico;
    }
}
