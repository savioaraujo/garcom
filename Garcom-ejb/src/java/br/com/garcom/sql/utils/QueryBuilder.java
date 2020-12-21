/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.sql.utils;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author evaldosavio
 */
public class QueryBuilder {

    private StringBuilder select;
    private StringBuilder join;
    private StringBuilder where;
    private StringBuilder orderBy;
    private String entityParam;
    private String entityName;
    private Map<String, Object> params;

    public static QueryBuilder instance(Class klass) {
        String entityName = klass.getSimpleName();
        String entityParam = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
        QueryBuilder queryBuilder = new QueryBuilder(" SELECT " + entityParam + " FROM " + entityName + " " + entityParam + " WHERE " + entityParam + ".active = TRUE ");
        queryBuilder.setEntityName(entityName);
        queryBuilder.setEntityParam(entityParam);
        return queryBuilder;
    }

    public QueryBuilder() {
        this.select = new StringBuilder();
        this.join = new StringBuilder();
        this.where = new StringBuilder();
        this.orderBy = new StringBuilder();
        this.params = new HashMap<String, Object>();
    }

    public QueryBuilder(String select) {
        this();
        this.select.append(select);

    }

    public void addWhere(String value) {
        this.where.append(" ").append(value);
    }

    public void addJoin(String value) {
        this.join.append(" ").append(value);
    }

    public void addOrderBy(String value) {
        this.orderBy.append(" ").append(value);
    }

    public StringBuilder getJoin() {
        return join;
    }

    public StringBuilder getOrderBy() {
        return orderBy;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public StringBuilder getSelect() {
        return select;
    }

    public StringBuilder getWhere() {
        return where;
    }

    public String getEntityParam() {
        return entityParam;
    }

    public void setEntityParam(String entityParam) {
        this.entityParam = entityParam;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void addParam(String name, Object value) {
        this.params.put(name, value);
    }

    public Query makeQuery(EntityManager entityManager) {

        Query query = entityManager.createQuery(select.toString().concat(join.toString()).concat(where.toString()).concat(orderBy.toString()));
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(key, value);
        }
        return query;
    }
}
