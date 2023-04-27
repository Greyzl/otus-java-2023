package ru.otus.repository;

import ru.otus.entity.MenuItem;
import ru.otus.processor.MenuProcessor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MenuItemRepository {

    void add(MenuItem menuItem);

    Optional<MenuItem> getById(int id);

    List<MenuItem> getAll();

    List<MenuItem> getListSortedById();
}
