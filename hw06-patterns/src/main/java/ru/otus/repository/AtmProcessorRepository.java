package ru.otus.repository;

import ru.otus.entity.MenuItem;
import ru.otus.processor.ATMProcessor;

import java.util.Optional;
import java.util.Set;

public interface AtmProcessorRepository {

    void add(MenuItem menuItem, ATMProcessor atmProcessor);

    Optional<ATMProcessor> get(MenuItem menuItem);

    Optional<ATMProcessor> getByMenuId(int id);

    Set<MenuItem> getMenuItems();
}
