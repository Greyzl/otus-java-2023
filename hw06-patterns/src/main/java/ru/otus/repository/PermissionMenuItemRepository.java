package ru.otus.repository;

import ru.otus.entity.MenuItem;
import ru.otus.entity.enums.Permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PermissionMenuItemRepository {

    void add(MenuItem menuItem, Permission permission);

    Optional<Permission> getPermission(MenuItem menuItem);

    Optional<Permission> getPermissionByMenuItemId(int id);

    Set<MenuItem> getMenuItems();
}
