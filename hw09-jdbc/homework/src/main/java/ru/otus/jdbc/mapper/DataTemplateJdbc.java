package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData<T> entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        System.out.println(entitySQLMetaData.getSelectByIdSql());
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(),
                List.of(id), entitySQLMetaData::mapResultSetToList).orElseThrow().stream().findFirst();
    }

    @Override
    public List<T> findAll(Connection connection) {
        System.out.println(entitySQLMetaData.getSelectAllSql());
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(),
                Collections.emptyList(),entitySQLMetaData::mapResultSetToList).orElseThrow();
    }

    @Override
    public long insert(Connection connection, T client) {
        System.out.println(entitySQLMetaData.getInsertSql());
        return dbExecutor.executeStatement(connection,
                entitySQLMetaData.getInsertSql(), entitySQLMetaData.getInsertQueryParams(client));
    }

    @Override
    public void update(Connection connection, T client) {
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                entitySQLMetaData.getUpdateQueryParams(client));
    }
}
