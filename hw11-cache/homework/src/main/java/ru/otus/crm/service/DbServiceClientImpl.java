package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> dataTemplate;
    private final TransactionRunner transactionRunner;

    private final HwCache<Long, Client> clientHwCache;

    public DbServiceClientImpl(TransactionRunner transactionRunner,
                               DataTemplate<Client> dataTemplate) {
        this.transactionRunner = transactionRunner;
        this.dataTemplate = dataTemplate;
        this.clientHwCache = null;
    }

    public DbServiceClientImpl(TransactionRunner transactionRunner,
                               DataTemplate<Client> dataTemplate,
                               HwCache<Long, Client> clientHwCache) {
        this.transactionRunner = transactionRunner;
        this.dataTemplate = dataTemplate;
        this.clientHwCache = clientHwCache;
    }

    @Override
    public Client saveClient(Client client) {
        Client savedClient = transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                var clientId = dataTemplate.insert(connection, client);
                var createdClient = new Client(clientId, client.getName());
                log.info("created client: {}", createdClient);
                return createdClient;
            }
            dataTemplate.update(connection, client);
            log.info("updated client: {}", client);
            return client;
        });
        if (clientHwCache != null){
            clientHwCache.put(savedClient.getId(), savedClient);
        }
        return savedClient;
    }

    @Override
    public Optional<Client> getClient(long id) {
        Optional<Client> mayByClient;
        if (clientHwCache != null){
            mayByClient = Optional.ofNullable(clientHwCache.get(id));
            if (mayByClient.isPresent()){
                return mayByClient;
            }
        }
        mayByClient = transactionRunner.doInTransaction(connection -> {
            var clientOptional = dataTemplate.findById(connection, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
        if (clientHwCache != null){
            mayByClient.ifPresent(client -> clientHwCache.put(client.getId(), client));
        }
        return mayByClient;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = transactionRunner.doInTransaction(connection -> {
            var clientList = dataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
       });
        if (clientHwCache != null){
            clients.forEach(client -> clientHwCache.put(client.getId(), client));
        }
        return clients;
    }
}
