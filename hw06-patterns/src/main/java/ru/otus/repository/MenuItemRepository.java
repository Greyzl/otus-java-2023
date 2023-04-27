package ru.otus.repository;

import ru.otus.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository {

    void add(MenuItem menuItem);

    Optional<MenuItem> getById(int id);

    List<MenuItem> getListSortedById();
}
