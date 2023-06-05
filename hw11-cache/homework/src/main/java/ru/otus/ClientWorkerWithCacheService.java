package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.mapper.DataTemplateJdbc;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.impl.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.impl.EntitySQLMetaDataImpl;

public class ClientWorkerWithCacheService implements AppRunner {

    private static final Logger log = LoggerFactory.getLogger(ClientWorkerWithCacheService.class);

    private final TransactionRunner transactionRunner;

    private final DbExecutor dbExecutor;

    public ClientWorkerWithCacheService(TransactionRunner transactionRunner, DbExecutor dbExecutor){
        this.transactionRunner = transactionRunner;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public void run() {
        // Работа с клиентом
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        EntitySQLMetaData<Client> entitySQLMetaDataClient = new EntitySQLMetaDataImpl<>(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient); //реализация DataTemplate, универсальная
        HwCache<Long, Client> clientHwCache = new MyCache<>();

        HwListener<Long, Client> clientHwListener = new HwListener<Long, Client>() {
            @Override
            public void notify(Long key, Client value, String action) {
                log.info(String.format("Operation with cache. key: %d, value: %s, operation: %s", key, value, action));
            }
        };

        clientHwCache.addListener(clientHwListener);
// Код дальше должен остаться
        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient, clientHwCache);

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
//Without cache: 6311
//With cache: 3674