package br.com.garcom.basico.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author laverson
 */
@Entity
public class Estado extends BasicLoggedBean implements Serializable {

    private String sigla;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
