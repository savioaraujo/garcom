/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.model.Module;
import br.com.garcom.main.ServicoGenericoBasico;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class ModuleServico extends ServicoGenericoBasico<Module> {
    
    public List<Module> autocompletar(String texto) {
        return findFilter("name", "%" + texto + "%");
    }
    
    public List<Module> pesquisar(Module entidade) {
        return find(entidade);
    }
}
