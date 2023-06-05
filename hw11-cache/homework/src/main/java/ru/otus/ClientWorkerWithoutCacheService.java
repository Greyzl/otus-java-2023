package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.mapper.DataTemplateJdbc;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.impl.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.impl.EntitySQLMetaDataImpl;

public class ClientWorkerWithoutCacheService implements AppRunner{
    private static final Logger log = LoggerFactory.getLogger(ClientWorkerWithoutCacheService.class);

    private final TransactionRunner transactionRunner;

    private final DbExecutor dbExecutor;

    public ClientWorkerWithoutCacheService(TransactionRunner transactionRunner, DbExecutor dbExecutor){
        this.transactionRunner = transactionRunner;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public void run() {
        // Работа с клиентом
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        EntitySQLMetaData<Client> entitySQLMetaDataClient = new EntitySQLMetaDataImpl<>(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient); //реализация DataTemplate, универсальная

// Код дальше должен остаться
        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        for (int i = 0; i < 5000; i++){
            Client client = dbServiceClient.getClient(i+1).orElseThrow();
            log.info("Client: {}", client.getName());
        }
        for (int i = 0; i < 5000; i++){
            Client client = dbServiceClient.getClient(i+1).orElseThrow();
            log.info("Client: {}", client.getName());
        }
    }
}

