package ru.otus.repository;

import ru.otus.entity.MenuItem;
import ru.otus.entity.enums.Permission;

import java.util.Optional;

public interface MenuPermissionRepository {

    void add(MenuItem menuItem, Permission permission);

    Optional<Permission> getByMenuItem(MenuItem menuItem);

}
