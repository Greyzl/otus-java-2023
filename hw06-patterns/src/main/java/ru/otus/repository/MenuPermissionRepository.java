package ru.otus.repository;

import ru.otus.entity.MenuItem;
import ru.otus.entity.enums.Permission;

import java.util.Optional;
import java.util.Set;

public interface MenuPermissionRepository {

    void add(MenuItem menuItem, Permission permission);

    Optional<Permission> getByMenuItem(MenuItem menuItem);

}
