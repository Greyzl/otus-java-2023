package ru.otus.jdbc.mapper.impl;

import ru.otus.core.repository.DataTemplateException;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    private final Field idField;

    private final List<Field> fields;

    private final List<Field> fieldsWithoutId;

    private final String tableName;

    private final Constructor<T> constructor;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData){
        this.tableName = entityClassMetaData.getName() ;
        this.fields = entityClassMetaData.getAllFields();
        this.idField = entityClassMetaData.getIdField();
        this.fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        this.constructor = entityClassMetaData.getConstructor();

        fieldsWithoutId.forEach(field -> field.setAccessible(true));
        fields.forEach(field -> field.setAccessible(true));
        idField.setAccessible(true);
    }
    @Override
    public String getSelectAllSql() {
        return "select " + allSqlFieldsAsStringForQuery() + " from " + tableName;
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectAllSql() + " where " + idField.getName() + "= ?";
    }

    @Override
    public String getInsertSql() {
        return String.format("insert into %s (%s) values(%s)", tableName,
                sqlFieldsNamesWithoutIdAsStringForInsertQuery(),
                sqlParametersForInsertQueryAsString());
    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set %s where %s = ?",
                tableName, sqlFieldsSetAsStringForUpdateQuery(), idField.getName());
    }

    @Override
    public List<T> mapResultSetToList(ResultSet resultSet) {
        try {
            List<T> resultList = new ArrayList<>();
            while (resultSet.next()){
                T object = constructor.newInstance();
                idField.set(object, resultSet.getLong(idField.getName()));
                for (Field field: fieldsWithoutId){
                    if (field.getType() == Long.class){
                        field.set(object, resultSet.getLong(field.getName()));
                    } else {
                        field.set(object, resultSet.getString(field.getName()));
                    }
                }
                resultList.add(object);
            }
            return resultList;
        } catch (SQLException | IllegalArgumentException |
                 IllegalAccessException | InstantiationException | InvocationTargetException e){
            throw new DataTemplateException(e);
        }
    }

    @Override
    public List<Object> getInsertQueryParams(T object) {
        List<Object> params = new ArrayList<>();
        try {
            for (Field field : fieldsWithoutId){
                var fieldValue = field.get(object);
                params.add(fieldValue);
            }
            return params;
        } catch (IllegalAccessException | IllegalArgumentException e){
            throw new DataTemplateException(e);
        }
    }

    @Override
    public List<Object> getUpdateQueryParams(T object) {
        try {
            List<Object> params = getInsertQueryParams(object);
            long idValue = idField.getLong(object);
            params.add(idValue);
            return params;
        } catch (IllegalAccessException | IllegalArgumentException e){
            throw new DataTemplateException(e);
        }
    }

    private String allSqlFieldsAsStringForQuery(){
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < fields.size(); index ++){
            Field field = fields.get(index);
            builder.append(field.getName());
            if (index != fields.size() - 1){
                builder.append(", ");
            }
        }
        return builder.toString();
    }
    private String sqlFieldsNamesWithoutIdAsStringForInsertQuery(){
        StringBuilder builder = new StringBuilder();
        int fieldCount = fieldsWithoutId.size();
        int index = 0;
        for (Field field: fieldsWithoutId){
            builder.append(field.getName());
            if (index != fieldCount - 1){
                builder.append(",");
            }
            index ++;
        }
        return builder.toString();
    }
    private String sqlParametersForInsertQueryAsString(){
        StringBuilder builder = new StringBuilder();
        int fieldCount = fieldsWithoutId.size();
        int index = 0;
        for (Field ignored : fieldsWithoutId){
            builder.append("?");
            if (index != fieldCount - 1){
                builder.append(",");
            }
            index ++;
        }
        return builder.toString();
    }
    private String sqlFieldsSetAsStringForUpdateQuery(){
        StringBuilder builder = new StringBuilder();
        int fieldCount = fieldsWithoutId.size();
        int index = 0;
        for (Field field: fieldsWithoutId){
            builder.append(field.getName()).append("=? ");
            if (index != fieldCount - 1){
                builder.append(",");
            }
            index ++;
        }
        return builder.toString();
    }
}
