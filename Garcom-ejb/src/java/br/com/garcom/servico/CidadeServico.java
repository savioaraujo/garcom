package br.com.garcom.servico;

import br.com.foxinline.util.Utils;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.basico.modelo.Cidade;
import br.com.garcom.utils.Caracteres;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class CidadeServico extends ServicoGenerico<Cidade> {

    public List<Cidade> autocompletar(String texto) {
        Cidade cidade = new Cidade();
        cidade.setNome(texto);
        return pesquisar(cidade);
    }

    @Override
    public void save(Cidade bean) {
        bean.setSlugNome(Caracteres.removerAcentuacao(bean.getNome()));
        super.save(bean);
    }

    @Override
    public Cidade update(Cidade bean) {
        bean.setSlugNome(Caracteres.removerAcentuacao(bean.getNome()));
        return super.update(bean);
    }

    @Override
    public List<Cidade> pesquisar(Cidade entidade) {

        String sql = "select cidade from " + Cidade.class.getSimpleName() + " cidade where cidade.active = true ";

        if (!Utils.isEmpty(entidade.getNome())) {
            sql += "and lower(cidade.slugNome) like '%" + Caracteres.removerAcentuacao(entidade.getNome().toLowerCase()) + "%'";
        }
        Query query = getEntityManager().createQuery(sql);
        query.setMaxResults(20);
        return query.getResultList();
    }
}
