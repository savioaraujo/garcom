/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.basico.modelo.Configuracoes;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class ConfiguracoesServico extends ServicoGenerico<Configuracoes> {

    @Override
    public List<Configuracoes> pesquisar(Configuracoes entidade) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
