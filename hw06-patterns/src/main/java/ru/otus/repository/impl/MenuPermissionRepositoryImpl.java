package ru.otus.repository.impl;

import ru.otus.entity.MenuItem;
import ru.otus.entity.enums.Permission;
import ru.otus.repository.MenuPermissionRepository;

import java.util.*;

public class MenuPermissionRepositoryImpl implements MenuPermissionRepository {

    Map<MenuItem, Permission> menuItems = new HashMap<>();

    @Override
    public void add(MenuItem menuItem, Permission permission) {
        menuItems.put(menuItem, permission);
    }

    @Override
    public Optional<Permission> getByMenuItem(MenuItem menuItem){
        return Optional.ofNullable(menuItems.get(menuItem));
    }

}
