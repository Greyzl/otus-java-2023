package ru.otus.repository.impl;

import ru.otus.entity.MenuItem;
import ru.otus.repository.MenuItemRepository;

import java.util.*;

public class MenuItemRepositoryImpl implements MenuItemRepository {
    private final List<MenuItem> menuItems = new ArrayList<>();

    @Override
    public void add(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    @Override
    public Optional<MenuItem> getById(int id) {
        return menuItems.stream().filter(menuItem -> menuItem.id() == id).findFirst();
    }

    @Override
    public List<MenuItem> getListSortedById(){
        return menuItems.stream().sorted().toList();
    }
}
