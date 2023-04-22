package ru.otus.repository.impl;

import ru.otus.entity.MenuItem;
import ru.otus.entity.enums.Permission;
import ru.otus.repository.PermissionMenuItemRepository;

import java.util.*;

public class PermissionMenuItemRepositoryImpl implements PermissionMenuItemRepository {

    Map<MenuItem, Permission> menuItems = new HashMap<>();

    @Override
    public void add(MenuItem menuItem, Permission permission) {
        menuItems.put(menuItem, permission);
    }

    @Override
    public Optional<Permission> getPermission(MenuItem menuItem){
        return Optional.ofNullable(menuItems.get(menuItem));
    }

    @Override
    public Optional<Permission> getPermissionByMenuItemId(int id) {
        MenuItem menuItem = new MenuItem(id, "stub");
        return Optional.ofNullable(menuItems.get(menuItem));
    }

    @Override
    public Set<MenuItem> getMenuItems() {
        return menuItems.keySet();
    }


}
