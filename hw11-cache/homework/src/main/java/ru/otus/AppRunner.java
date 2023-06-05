package ru.otus;

import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.core.sessionmanager.TransactionRunner;

public interface AppRunner {

    void run();
}
