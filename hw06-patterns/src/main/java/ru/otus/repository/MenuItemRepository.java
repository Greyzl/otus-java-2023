package ru.otus.repository;

import ru.otus.entity.MenuItem;

import java.util.List;

public interface MenuItemRepository {

    void addMenuItem(MenuItem menuItem);

    MenuItem getMenuItem(int id);

    List<String> getMenuItemLabels();
}
