package ru.otus.service.impl;

import ru.otus.entity.MenuItem;
import ru.otus.entity.enums.Permission;
import ru.otus.exception.MenuItemNotExistException;
import ru.otus.formatter.MenuItemFormatter;
import ru.otus.repository.MenuItemRepository;
import ru.otus.repository.MenuPermissionRepository;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;
import ru.otus.service.PermissionService;

import java.util.Optional;

public class PermissionServiceImpl implements PermissionService {

    private final MenuItemRepository menuItemRepository;

    private final MenuPermissionRepository menuPermissionRepository;

    private final MenuItemFormatter menuItemFormatter= new MenuItemFormatter();

    private final OutputService outputService;

    private final InputService inputService;

    public PermissionServiceImpl(MenuItemRepository menuItemRepository,
                                 MenuPermissionRepository menuPermissionRepository,
                                 OutputService outputService,
                                 InputService inputService){
        this.menuItemRepository = menuItemRepository;
        this.menuPermissionRepository = menuPermissionRepository;
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
            Optional<MenuItem> mayBeMenuItem = menuItemRepository.getById(permissionOption);
            if (mayBeMenuItem.isEmpty()){
                throw new MenuItemNotExistException(menuItemNotFoundMessage(permissionOption));
            }
            MenuItem selectedMenuItem = mayBeMenuItem.get();
            Optional<Permission> maybePermission = menuPermissionRepository.getByMenuItem(selectedMenuItem);
            return maybePermission.orElse(Permission.DENIED);
        }catch (NumberFormatException e){
            outputService.print("Incorrect input.");
            return Permission.DENIED;
        }
    }

    private String preparePermissionQuestion(String question){
        StringBuilder builder = new StringBuilder(question).append("\n");
        for (MenuItem menuItem: menuItemRepository.getListSortedById()){
            builder.append(menuItemFormatter.format(menuItem)).append("\n");
        }
        builder.append("Please select option digit");
        return builder.toString();
    }

    private String menuItemNotFoundMessage(int id){
        return "Menu item with id: " + id + " not found";
    }
}
