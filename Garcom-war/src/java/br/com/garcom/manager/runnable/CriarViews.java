/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.manager.runnable;

import br.com.foxinline.util.runable.GenerateView;
import br.com.garcom.estoque.modelo.MovimentacaoEstoque;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author evaldosavio
 */
public class CriarViews {

    public static void main(String[] args) throws FileNotFoundException {
        List<Class<MovimentacaoEstoque>> klasses = Arrays.asList(MovimentacaoEstoque.class);
        for (Class klass : klasses) {

            print(GenerateView.getCreateView(klass, "template/service.vm", "Criar"), new File("../Garcom-ejb/src/java/br/com/garcom/servico/" + klass.getSimpleName() + "Servico.java"));

            print(GenerateView.getCreateView(klass, "template/criar.vm", "Criar"), new File("web/criar" + klass.getSimpleName() + ".xhtml"));
            print(GenerateView.getCreateView(klass, "template/visualizar.vm", "Pesquisar"), new File("web/visualizar" + klass.getSimpleName() + ".xhtml"));
            print(GenerateView.getCreateView(klass, "template/index.vm", "Pesquisar"), new File("web/pesquisar" + klass.getSimpleName() + ".xhtml"));

            print(GenerateView.getCreateView(klass, "template/managers/managerCadastrar.vm", "Pesquisar"), new File("src/java/br/com/garcom/manager/criar/ManagerCriar" + klass.getSimpleName() + ".java"));
            print(GenerateView.getCreateView(klass, "template/managers/managerPesquisar.vm", "Pesquisar"), new File("src/java/br/com/garcom/manager/pesquisar/ManagerPesquisar" + klass.getSimpleName() + ".java"));

        }
    }

    private static void print(String string, File file) throws FileNotFoundException {
        PrintStream print = new PrintStream(file);
        print.print(string);
        print.flush();
        print.close();
    }
}
