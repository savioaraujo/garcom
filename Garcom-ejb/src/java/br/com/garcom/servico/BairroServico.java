package br.com.garcom.servico;

import br.com.foxinline.util.Utils;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.basico.modelo.Bairro;
import br.com.garcom.basico.modelo.Cidade;
import br.com.garcom.utils.Caracteres;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author laverson
 */
@Stateless
public class BairroServico extends ServicoGenerico<Bairro> {

    public List<Bairro> autocompletar(String texto) {
        Bairro bairro = new Bairro();
        bairro.setNome(texto);
        return pesquisar(bairro);
    }

    @Override
    public void save(Bairro bean) {
        bean.setSlugNome(Caracteres.removerAcentuacao(bean.getNome()));
        super.save(bean);
    }

    @Override
    public Bairro update(Bairro bean) {
        bean.setSlugNome(Caracteres.removerAcentuacao(bean.getNome()));
        return super.update(bean);
    }

    @Override
    public List<Bairro> pesquisar(Bairro entidade) {

        String sql = "select bairro from " + Bairro.class.getSimpleName() + " bairro ";

        if (entidade.getCidade() != null) {
            sql += "join bairro.cidade cidade ";
        }

        sql += "where bairro.active = true ";

        if (!Utils.isEmpty(entidade.getNome())) {
            sql += "and lower(bairro.slugNome) like '%" + Caracteres.removerAcentuacao(entidade.getNome().toLowerCase()) + "%'";
        }
        if (entidade.getCidade() != null) {
            sql += " and cidade = :cidade";
        }

        Query query = getEntityManager().createQuery(sql);

        if (entidade.getCidade() != null) {
            query.setParameter("cidade", entidade.getCidade());
        }

        return query.getResultList();
    }

    public List<Bairro> autocompletar(String nome, Cidade cidade) {
        String sql = "select bairro from " + Bairro.class.getSimpleName() + " bairro ";

        if (cidade != null) {
            sql += "join bairro.cidade cidade ";
        }

        sql += "where bairro.active = true ";

        if (!Utils.isEmpty(nome)) {
            sql += "and lower(bairro.slugNome) like '%" + Caracteres.removerAcentuacao(nome).toLowerCase() + "%'";
        }
        if (cidade != null) {
            sql += " and cidade = :cidade";
        }

        Query query = getEntityManager().createQuery(sql);

        if (cidade != null) {
            query.setParameter("cidade", cidade);
        }
        query.setMaxResults(20);
        return query.getResultList();
    }
}
