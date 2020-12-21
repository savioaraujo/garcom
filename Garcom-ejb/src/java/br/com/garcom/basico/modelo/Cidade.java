package br.com.garcom.basico.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author laverson
 */
@Entity
public class Cidade extends BasicLoggedBean implements Serializable {

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false, updatable = false)
    private Estado estado;
    private String nome;
    @Column(name = "slug_nome")
    private String slugNome;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSlugNome() {
        return slugNome;
    }

    public void setSlugNome(String slugNome) {
        this.slugNome = slugNome;
    }
}
