/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.garcom.enums.TipoCliente;
import br.com.foxinline.util.Utils;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.basico.modelo.TipoDocumento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class TipoDocumentoServico extends ServicoGenerico<TipoDocumento> {

    public List<TipoDocumento> autocompletar(String texto) {
        return findAll();
    }

    public List<TipoDocumento> pesquisar(TipoDocumento entidade, TipoCliente utilizado, TipoCliente obrigatorio) {
        String sql = "SELECT tipo FROM TipoDocumento tipo"
                + " LEFT JOIN tipo.utilizadoEm utilizado"
                + " LEFT JOIN tipo.obrigatorioEm obrigatorio"
                + " WHERE tipo.active = TRUE";

        if (!Utils.isEmpty(entidade.getDescricao())) {
            sql += " AND lower(tipo.descricao) LIKE :descricao";
        }
        if (utilizado != null) {
            sql += " AND utilizado = :utilizado";
        }
        if (obrigatorio != null) {
            sql += " AND obrigatorio = :obrigatorio";
        }
        Query query = getEntityManager().createQuery(sql);
        if (!Utils.isEmpty(entidade.getDescricao())) {
            query.setParameter("descricao", "%" + entidade.getDescricao().toLowerCase() + "%");
        }
        if (utilizado != null) {
            query.setParameter("utilizado", utilizado);
        }
        if (obrigatorio != null) {
            query.setParameter("obrigatorio", obrigatorio);
        }

        return query.getResultList();
    }

    public List<TipoDocumento> autocompletar(String texto, TipoCliente tipoCadastro) {
        String sql = "SELECT tipo FROM TipoDocumento tipo"
                + " INNER JOIN tipo.utilizadoEm tipoCadastro"
                + " WHERE tipo.active = TRUE AND tipoCadastro = :pescador";

        if (!Utils.isEmpty(texto)) {
            sql += " AND lower(tipo.descricao) LIKE :descricao";
        }
        Query query = getEntityManager().createQuery(sql);
        if (!Utils.isEmpty(texto)) {
            query.setParameter("descricao", "%" + texto.toLowerCase() + "%");
        }
        query.setParameter("pescador", tipoCadastro);

        return query.getResultList();

    }

    @Override
    public List<TipoDocumento> pesquisar(TipoDocumento entidade) {
        return findAll();
    }
}
