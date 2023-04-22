package ru.otus.repository;

import ru.otus.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository {

    void addMenuItem(MenuItem menuItem);

    Optional<MenuItem> getMenuItem(int id);

    List<MenuItem> getMenuItemList();
}
