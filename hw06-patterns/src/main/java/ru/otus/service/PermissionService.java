package ru.otus.service;

import ru.otus.entity.enums.Permission;

public interface PermissionService {

    Permission askForPermission(String question);
}
