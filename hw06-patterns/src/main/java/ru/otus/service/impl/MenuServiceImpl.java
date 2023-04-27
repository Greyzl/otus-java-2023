package ru.otus.service.impl;

import ru.otus.entity.MenuItem;
import ru.otus.exception.MenuItemNotExistException;
import ru.otus.formatter.MenuItemFormatter;
import ru.otus.processor.MenuProcessor;
import ru.otus.repository.MenuItemRepository;
import ru.otus.service.MenuService;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;

import java.util.List;
import java.util.Optional;

public class MenuServiceImpl implements MenuService {

    private final OutputService outputService;

    private final InputService inputService;

    private final MenuItemRepository menuItemRepository;

    private final MenuItemFormatter menuItemFormatter = new MenuItemFormatter();

    public MenuServiceImpl(OutputService outputService,
                           InputService inputService,
                           MenuItemRepository menuItemRepository){
        this.outputService = outputService;
        this.inputService = inputService;
        this.menuItemRepository = menuItemRepository;
    }
    @Override
    public MenuItem getSelectedMenuItem() {
        while (true){
            try {
                outputService.print(getMenuItemListMessage());
                String userInput = inputService.read();
                int menuItemId = Integer.parseInt(userInput);
                Optional<MenuItem> mayBeMenuItem = menuItemRepository.getById(menuItemId);
                if (mayBeMenuItem.isEmpty()){
                    throw new MenuItemNotExistException("Such option doesn't exist. id:" + menuItemId);
                }
                return mayBeMenuItem.get();
            } catch (RuntimeException exception){
                outputService.print("Incorrect input. Please, try again.");
            }
        }
    }

    private String getMenuItemListMessage(){
        List<MenuItem> menuItemListSortedById = menuItemRepository.getListSortedById();
        StringBuilder builder = new StringBuilder();
        builder.append("Available options: \n");
        for (MenuItem menuItem: menuItemListSortedById){
            builder.append(menuItemFormatter.format(menuItem)).append("\n");
        }
        builder.append("Please, enter digit of option");
        return builder.toString();
    }
}
