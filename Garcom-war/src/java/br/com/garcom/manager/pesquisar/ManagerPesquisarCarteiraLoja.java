/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.pesquisar;

import br.com.garcom.servico.CarteiraLojaServico;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.financeiro.modelo.CarteiraLoja;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author evaldosavio
 */
@ManagedBean
@ViewScoped
public class ManagerPesquisarCarteiraLoja {

    @EJB
    private CarteiraLojaServico carteiraLojaServico;
    private CarteiraLoja carteiraLoja;
    private List<CarteiraLoja> carteiraLojas;

    @PostConstruct
    private void init() {
        instanciar();
        delegar();
    }

    private void instanciar() {
        this.carteiraLoja = new CarteiraLoja();
    }

    private void delegar() {
        Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String visualizarId = parametros.get("id_carteiraLoja");

        if (visualizarId != null) {
            this.carteiraLoja = carteiraLojaServico.load(Long.parseLong(visualizarId));
        }
    }

    public void pesquisar() {
        this.carteiraLojas = this.carteiraLojaServico.find(carteiraLoja);
    }

    public void excluirCarteiraLoja() {
        this.carteiraLojaServico.inactivate(carteiraLoja);
        Mensagem.messagemInfoRedirect("CarteiraLoja excluida com sucesso.", "index.xhtml");
    }

    public List<CarteiraLoja> autocomplete(String descricao) {
        return carteiraLojaServico.autocompletar(descricao);
    }

    public CarteiraLoja getCarteiraLoja() {
        return carteiraLoja;
    }

    public void setCarteiraLoja(CarteiraLoja carteiraLoja) {
        this.carteiraLoja = carteiraLoja;
    }

    public List<CarteiraLoja> getCarteiraLojas() {
        return carteiraLojas;
    }

    public void setCarteiraLojas(List<CarteiraLoja> carteiraLojas) {
        this.carteiraLojas = carteiraLojas;
    }
}
