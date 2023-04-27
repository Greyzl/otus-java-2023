package ru.otus.repository;

import ru.otus.entity.MenuItem;
import ru.otus.processor.MenuProcessor;

public interface MenuProcessorRepository {

    MenuProcessor getByMenuItem(MenuItem menuItem);

    void add(MenuItem menuItem, MenuProcessor menuProcessor);
}
