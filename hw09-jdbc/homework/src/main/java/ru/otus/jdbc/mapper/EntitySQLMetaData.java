package ru.otus.jdbc.mapper;

import java.sql.ResultSet;
import java.util.List;

/**
 * Создает SQL - запросы
 */
public interface EntitySQLMetaData<T> {
    String getSelectAllSql();

    String getSelectByIdSql();

    String getInsertSql();

    String getUpdateSql();

    List<T> mapResultSetToList(ResultSet resultSet);

    List<Object> getInsertQueryParams(T object);

    List<Object> getUpdateQueryParams(T object);
}
