package ru.otus.service.impl;

import ru.otus.entity.MenuItem;
import ru.otus.entity.enums.Permission;
import ru.otus.exception.IncorrectMenuItemException;
import ru.otus.formatter.MenuItemFormatter;
import ru.otus.repository.PermissionMenuItemRepository;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;
import ru.otus.service.PermissionService;

import java.util.Optional;

public class PermissionServiceImpl implements PermissionService {

    private final PermissionMenuItemRepository permissionMenuItemRepository;

    private final MenuItemFormatter menuItemFormatter= new MenuItemFormatter();

    private final OutputService outputService;

    private final InputService inputService;

    public PermissionServiceImpl(PermissionMenuItemRepository permissionMenuItemRepository,
                                 OutputService outputService,
                                 InputService inputService){
        this.permissionMenuItemRepository = permissionMenuItemRepository;
        this.outputService = outputService;
        this.inputService = inputService;
    }

    @Override
    public Permission askForPermission(String question) {
        try {
            String preparedQuestion = preparePermissionQuestion(question);
            outputService.print(preparedQuestion);
            String proceedInput = inputService.read();
            int permissionOption = Integer.parseInt(proceedInput);
            Optional<Permission> maybePermission = permissionMenuItemRepository.getPermissionByMenuItemId(permissionOption);
            return maybePermission.orElse(Permission.DENIED);
        }catch (NumberFormatException e){
            outputService.print("Incorrect input.");
            return Permission.DENIED;
        }
    }

    private String preparePermissionQuestion(String question){
        StringBuilder builder = new StringBuilder(question).append("\n");
        for (MenuItem menuItem: permissionMenuItemRepository.getMenuItems()){
            builder.append(menuItemFormatter.format(menuItem)).append("\n");
        }
        builder.append("Please select option digit");
        return builder.toString();
    }
}
