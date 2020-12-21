/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.criar;

import br.com.foxinline.annotations.AccessPermission;
import br.com.foxinline.util.Utils;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.manager.ManagerCriar;
import br.com.garcom.manager.helper.Mensagem;
import br.com.garcom.basico.modelo.Configuracoes;
import br.com.garcom.servico.ConfiguracoesServico;
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
@AccessPermission(moduleName = "ADMINISTRACAO_GERAL", pageRedirect = "index.xhtml")
public class ManagerCriarConfiguracoes extends ManagerCriar<Configuracoes> {

    @EJB
    private ConfiguracoesServico configuracoesServico;

    @Override
    protected void init() {
        super.init();
        List<Configuracoes> configuracoes = configuracoesServico.findFilter("id", 1);
        if (!Utils.isEmpty(configuracoes)) {
            this.entidade = configuracoes.get(0);
        }
    }

     public void salvar() {
        if (entidade.getId() == null) {
            getServico().save(entidade);
        } else {
            getServico().update(entidade);
        }

        Mensagem.messagemInfoRedirect("Salvo com sucesso !", "configuracoes.xhtml");

    }
    
    @Override
    protected ServicoGenerico getServico() {
        return configuracoesServico;
    }
}
