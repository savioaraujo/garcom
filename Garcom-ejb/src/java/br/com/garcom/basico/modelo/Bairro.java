package br.com.garcom.basico.modelo;

import br.com.foxinline.model.BasicLoggedBean;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author cadomeireles
 */
@Entity
public class Bairro extends BasicLoggedBean implements Serializable {

    private String nome;
    @Column(name = "slug_nome")
    private String slugNome;
    @OneToOne
    private Cidade cidade;

    public Bairro() {
    }

    public Bairro(Long id, String nome, Cidade cidade) {
        this.setId(id);
        this.nome = nome;
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getSlugNome() {
        return slugNome;
    }

    public void setSlugNome(String slugNome) {
        this.slugNome = slugNome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 13 * hash + (this.slugNome != null ? this.slugNome.hashCode() : 0);
        hash = 13 * hash + (this.cidade != null ? this.cidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bairro other = (Bairro) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if ((this.slugNome == null) ? (other.slugNome != null) : !this.slugNome.equals(other.slugNome)) {
            return false;
        }
        if (this.cidade != other.cidade && (this.cidade == null || !this.cidade.equals(other.cidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bairro{" + "nome=" + nome + ", slugNome=" + slugNome + ", cidade=" + cidade + '}';
    }
}
