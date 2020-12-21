/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.servico;

import br.com.foxinline.util.StringUtils;
import br.com.foxinline.util.Utils;
import br.com.garcom.main.ServicoGenerico;
import br.com.garcom.basico.modelo.Cliente;
import br.com.garcom.utils.Caracteres;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author evaldosavio
 */
@Stateless
public class ClienteServico extends ServicoGenerico<Cliente> {

    public List<Cliente> autocompletar(String texto) {

        String sql = "SELECT cliente FROM Cliente cliente WHERE cliente.active = TRUE";

        if (!Utils.isEmpty(texto)) {
            sql += " AND (cliente.slugNome LIKE :nome OR cliente.slugApelido LIKE :nome)";
        }
        sql += " ORDER BY cliente.slugNome";
        Query query = getEntityManager().createQuery(sql);
        if (!Utils.isEmpty(texto)) {
            query.setParameter("nome", "%" + StringUtils.removeCaracteresEspeciais(texto).toLowerCase() + "%");
        }
        query.setMaxResults(200);
        return query.getResultList();
    }

    public List<Cliente> autocompletarSemClienteFinal(String texto) {

        String sql = "SELECT cliente FROM Cliente cliente WHERE cliente.active = TRUE and lower(cliente.slugNome) not like '%final%'";

        if (!Utils.isEmpty(texto)) {
            sql += " AND (cliente.slugNome LIKE :nome OR cliente.slugApelido LIKE :nome)";
        }
        sql += " ORDER BY cliente.slugNome";
        Query query = getEntityManager().createQuery(sql);
        if (!Utils.isEmpty(texto)) {
            query.setParameter("nome", "%" + StringUtils.removeCaracteresEspeciais(texto).toLowerCase() + "%");
        }
        query.setMaxResults(200);
        return query.getResultList();
    }

    @Override
    public void save(Cliente bean) {
        validarCliente(bean);

        super.save(bean);
    }

    private void validarCliente(Cliente bean) {
        bean.setSlugNome(StringUtils.removeCaracteresEspeciais(bean.getNome()).toLowerCase());
        bean.setCpf(Caracteres.removecaracter(bean.getCpf()));
        if(bean.getEndereco() != null){
            bean.getEndereco().setCep(Caracteres.removecaracter(bean.getEndereco().getCep()));
        }
        if (!Utils.isEmpty(bean.getApelido())) {
            bean.setSlugApelido(StringUtils.removeCaracteresEspeciais(bean.getApelido()).toLowerCase());
        } else {
            bean.setApelido(null);
            bean.setSlugApelido(null);
        }
    }

    @Override
    public Cliente update(Cliente bean) {
        validarCliente(bean);
        return super.update(bean);
    }

    @Override
    public Cliente updateWithoutLog(Cliente bean) {
        validarCliente(bean);
        return super.updateWithoutLog(bean);
    }

    @Override
    public List<Cliente> pesquisar(Cliente entidade) {
        Map<String, Object> params = new HashMap<String, Object>();

        String sql = "SELECT cliente FROM Cliente cliente"
                + "\nWHERE cliente.active =  TRUE";

        if (!Utils.isEmpty(entidade.getNome())) {
            sql += "\n AND lower(cliente.slugNome) like :nome ";
            params.put("nome", "%" + entidade.getNome().toLowerCase() + "%");
        }
        if (!Utils.isEmpty(entidade.getApelido())) {
            sql += "\n AND lower(cliente.slugApelido) like :apelido ";
            params.put("apelido", "%" + StringUtils.removeCaracteresEspeciais(entidade.getApelido()).toLowerCase() + "%");
        }
        if (!Utils.isEmpty(entidade.getCpf())) {
            sql += "\n AND cliente.cpf = :cpf ";
            params.put("apelido", StringUtils.removeCaracteresEspeciais(entidade.getCpf()));
        }
        if (!Utils.isEmpty(entidade.getDci())) {
            sql += "\n AND lower(cliente.dci) like :dci ";
            params.put("dci", "%" + entidade.getDci().toLowerCase() + "%");
        }
        if (!Utils.isEmpty(entidade.getCgnid())) {
            sql += "\n AND lower(cliente.cgnid) like :cgnid ";
            params.put("cgnid", "%" + entidade.getCgnid().toLowerCase() + "%");
        }
        if (entidade.getDataNascimento() != null) {
            sql += "\n AND cliente.dataNascimento = :nascimento ";
            params.put("nascimento", entidade.getDataNascimento());
        }

        sql += "\n ORDER BY cliente.nome";

        Query query = getEntityManager().createQuery(sql);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String string = entry.getKey();
            Object object = entry.getValue();
            query.setParameter(string, object);
        }
        return query.getResultList();
    }
}
