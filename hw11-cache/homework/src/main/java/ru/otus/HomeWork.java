package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

        var clientCreatorService = new ClientCreatorService(transactionRunner, dbExecutor);
        clientCreatorService.run();

        var cacheAppRunner = new ClientWorkerWithCacheService(transactionRunner ,dbExecutor);
        long startDateTimeCache = System.currentTimeMillis();
        cacheAppRunner.run();
        long endDateTimeCache = System.currentTimeMillis();
        log.info("With cache: {}", endDateTimeCache - startDateTimeCache);

        var uncachedAppRunner = new ClientWorkerWithoutCacheService(transactionRunner ,dbExecutor);
        long startDateTimeUncache = System.currentTimeMillis();
        uncachedAppRunner.run();
        long endDateTimeUncache = System.currentTimeMillis();
        log.info("With cache: {}", endDateTimeCache - startDateTimeCache);
        log.info("Without cache: {}", endDateTimeUncache - startDateTimeUncache);

    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
