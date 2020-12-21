/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager;

import br.com.foxinline.util.DateUtils;
import br.com.foxinline.util.UserUtils;
import br.com.foxinline.util.Utils;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.financeiro.modelo.CarteiraLoja;
import br.com.garcom.venda.modelo.Venda;
import br.com.garcom.servico.CarteiraLojaServico;
import br.com.garcom.servico.VendaServico;
import br.com.garcom.usuario.modelo.Usuario;
import br.com.garcom.usuario.servico.UsuarioServico;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author foxinline
 */
@ViewScoped
@ManagedBean
public class ManagerPrincipal {

    @EJB
    UsuarioServico usuarioServico;
    @EJB
    VendaServico vendaServico;
    @EJB
    CarteiraLojaServico carteiraLojaServico;
    private Usuario usuarioLogado;
    private List<Venda> vendas;
    private Venda vendaRemocao;
    private Venda vendaFinalizar;
    private List<CarteiraLoja> carteirasInadimplentes;

    @PostConstruct
    public void init() {
        this.usuarioLogado = usuarioServico.getUserLogged();

    }

    public List<Venda> vendasAbertas() {
        if (this.vendas == null) {
            this.vendas = vendaServico.buscarVendaAberta();
            if (Utils.isEmpty(this.vendas)) {
                this.vendas = new ArrayList<Venda>();
            }
        }
        return this.vendas;
    }

    public List<CarteiraLoja> clientesInadimplentes() {
        if (this.carteirasInadimplentes == null) {
            this.carteirasInadimplentes = carteiraLojaServico.buscarClientesInadimplentes();
            if (Utils.isEmpty(this.carteirasInadimplentes)) {
                this.carteirasInadimplentes = new ArrayList<CarteiraLoja>();
            }
        }
        return this.carteirasInadimplentes;
    }

    public boolean acesso(String modulo) {
        if (this.usuarioLogado.getLogin().equals("admin")) {
            return true;
        } else {
            return UserUtils.hasAccess(usuarioLogado, modulo, UserUtils.Permission.SHOW);
        }
    }

    public boolean acesso(String modulo, String acao) {
        if (this.usuarioLogado.getLogin().equals("admin")) {
            return true;
        } else {
            return UserUtils.hasAccess(usuarioLogado, modulo, acao);
        }
    }

    public boolean exibirGraficoVenda() {
        return true;
    }
    
    public void montarGrafico() {
        if(exibirGraficoVenda()){
            
        }
    }

    public void logout() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.responseComplete();
        facesContext.getExternalContext().invalidateSession();
        facesContext.getExternalContext().redirect("login.jsp");
    }

    public void removerVenda() {
        vendaServico.inactivate(vendaRemocao);
        Mensagem.messagemInfo("Venda removida com sucesso!");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("confirmarRemocaoVenda.hide();");
        this.vendas = null;
    }

    public void pagar(Venda venda) {
        Mensagem.redirect("frenteCaixa.xhtml?id=" + venda.getId() + "&pagar=true");
    }

    public void finalizarVenda() {
        vendaFinalizar.setAberta(false);
        vendaFinalizar.setUsuarioFechamento(usuarioLogado);
        vendaFinalizar.setDataFechamento(new Date());
        vendaServico.update(vendaFinalizar);
        Mensagem.messagemInfo("Venda finalizada com sucesso!");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("fecharVendaDialog.hide();");
    }

    public String obterAno() {
        return DateUtils.format(new Date(), "yyyy");
    }

    public Date dataAtual() {
        return new Date();
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Venda getVendaFinalizar() {
        return vendaFinalizar;
    }

    public void setVendaFinalizar(Venda vendaFinalizar) {
        this.vendaFinalizar = vendaFinalizar;
    }

    public Venda getVendaRemocao() {
        return vendaRemocao;
    }

    public void setVendaRemocao(Venda vendaRemocao) {
        this.vendaRemocao = vendaRemocao;
    }

    public List<CarteiraLoja> getCarteirasInadimplentes() {
        return carteirasInadimplentes;
    }

    public void setCarteirasInadimplentes(List<CarteiraLoja> carteirasInadimplentes) {
        this.carteirasInadimplentes = carteirasInadimplentes;
    }
}
