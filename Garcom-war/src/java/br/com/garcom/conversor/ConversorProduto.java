/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.conversor;

import br.com.garcom.estoque.modelo.Produto;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author evaldosavio
 */
@FacesConverter(value = "conversor_produto", forClass = Produto.class)
public class ConversorProduto implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            Produto produto = (Produto) uiComponent.getAttributes().get(value);
//            if (produto == null) {
//                produto = new Produto();
//                produto.setDescricao(value);
//            }
            return produto;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Produto) {
            Produto entity = (Produto) value;
            if (entity != null && entity instanceof Produto && entity.getId() != null) {
                uiComponent.getAttributes().put(entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }
}
