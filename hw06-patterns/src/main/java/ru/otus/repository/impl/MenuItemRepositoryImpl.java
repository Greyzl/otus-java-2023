package ru.otus.repository.impl;

import ru.otus.entity.MenuItem;
import ru.otus.repository.MenuItemRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MenuItemRepositoryImpl implements MenuItemRepository {

    Map<Integer, MenuItem> menuItems = new HashMap<>();

    @Override
    public void addMenuItem(MenuItem menuItem) {
        int itemId = menuItem.getId();
        menuItems.put(itemId, menuItem);
    }

    @Override
    public Optional<MenuItem> getMenuItem(int id){
        return Optional.of(menuItems.get(id));
    }

    @Override
    public List<MenuItem> getMenuItemList() {
        return menuItems.values().stream().toList();
    }


}
