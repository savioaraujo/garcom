/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.utils;

import br.com.foxinline.util.MessageUtils;
import br.com.garcom.basico.modelo.Configuracoes;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;

/**
 *
 * @author evaldosavio
 */
public class RelatorioUtils {

    public static Map<String, Object> obterCabecalhoFormatado(Configuracoes configuracoes) {

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("nomeLocal", configuracoes.getNome());
        parametros.put("endereco", configuracoes.getEndereco());
        parametros.put("contatos", configuracoes.getContatos());

        return parametros;
    }

    public static void gerarRelatorio(Map<String, Object> parametros, Collection<?> collection, String url, String path) {
        try {

            if (parametros == null) {
                parametros = new HashMap<String, Object>();
            }
            parametros.put("SUBREPORT_DIR", new File(url).getParentFile().getPath() + File.separatorChar);
            JRSwapFile file = new JRSwapFile(path, 4096, 10);
            JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(10, file, true);

            parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
            parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));

            FacesContext facesContext = FacesContext.getCurrentInstance();

            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(url);
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            ServletOutputStream ouputStream = response.getOutputStream();

            JRDataSource jrds;

            if (collection != null) {
                jrds = new JRBeanCollectionDataSource(collection);
            } else {
                jrds = new JREmptyDataSource();
            }

            JasperRunManager.runReportToPdfStream(stream, ouputStream, parametros, jrds);

            response.setHeader("Content-Disposition", "inline; filename=\"relatorio.pdf\"");
            response.setContentType("application/pdf");
            facesContext.responseComplete();
            ouputStream.flush();
            ouputStream.close();

        } catch (IOException e) {
            MessageUtils.error("Erro ao gerar relatório!");

        } catch (JRException ex) {
            Logger.getLogger(RelatorioUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
