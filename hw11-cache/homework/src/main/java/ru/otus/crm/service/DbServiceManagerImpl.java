package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;

import java.util.List;
import java.util.Optional;

public class DbServiceManagerImpl implements DBServiceManager {
    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class);

    private final DataTemplate<Manager> managerDataTemplate;
    private final TransactionRunner transactionRunner;

    private final HwCache<Long, Manager> managerHwCache;

    public DbServiceManagerImpl(TransactionRunner transactionRunner, DataTemplate<Manager> managerDataTemplate) {
        this.transactionRunner = transactionRunner;
        this.managerDataTemplate = managerDataTemplate;
        this.managerHwCache = null;
    }

    public DbServiceManagerImpl(TransactionRunner transactionRunner,
                                DataTemplate<Manager> managerDataTemplate,
                                HwCache<Long, Manager> managerHwCache) {
        this.transactionRunner = transactionRunner;
        this.managerDataTemplate = managerDataTemplate;
        this.managerHwCache = managerHwCache;
    }

    @Override
    public Manager saveManager(Manager manager) {
        Manager savedManager = transactionRunner.doInTransaction(connection -> {
            if (manager.getNo() == null) {
                var managerNo = managerDataTemplate.insert(connection, manager);
                var createdManager = new Manager(managerNo, manager.getLabel(), manager.getParam1());
                log.info("created manager: {}", createdManager);
                return createdManager;
            }
            managerDataTemplate.update(connection, manager);
            log.info("updated manager: {}", manager);
            return manager;
        });
        if (managerHwCache != null){
            managerHwCache.put(savedManager.getNo(), savedManager);
        }
        return savedManager;
    }

    @Override
    public Optional<Manager> getManager(long no) {
        Optional<Manager> mayByManager;
        if (managerHwCache != null){
            mayByManager = Optional.ofNullable(managerHwCache.get(no));
            if (mayByManager.isPresent()){
                return mayByManager;
            }
        }
        mayByManager = transactionRunner.doInTransaction(connection -> {
            var managerOptional = managerDataTemplate.findById(connection, no);
            log.info("manager: {}", managerOptional);
            return managerOptional;
        });
        if (managerHwCache != null){
            mayByManager.ifPresent(client -> managerHwCache.put(client.getNo(), client));
        }
        return mayByManager;
    }

    @Override
    public List<Manager> findAll() {
        List<Manager> mangers = transactionRunner.doInTransaction(connection -> {
            var managerList = managerDataTemplate.findAll(connection);
            log.info("managerList:{}", managerList);
            return managerList;
       });
        if (managerHwCache != null){
            mangers.forEach(manager -> managerHwCache.put(manager.getNo(), manager));
        }
        return mangers;
    }
}
