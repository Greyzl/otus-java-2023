package ru.otus.service.impl;

import ru.otus.entity.MenuItem;
import ru.otus.exception.MenuItemNotExistException;
import ru.otus.formatter.MenuItemFormatter;
import ru.otus.processor.ATMProcessor;
import ru.otus.repository.MenuItemRepository;
import ru.otus.service.AtmMenuService;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;

import java.util.Optional;

public class AtmMenuServiceImpl implements AtmMenuService {

    private final OutputService outputService;

    private final InputService inputService;

    private final MenuItemRepository menuItemRepository;

    private final MenuItemFormatter menuItemFormatter = new MenuItemFormatter();

    public AtmMenuServiceImpl(OutputService outputService,
                              InputService inputService,
                              MenuItemRepository menuItemRepository){
        this.outputService = outputService;
        this.inputService = inputService;
        this.menuItemRepository = menuItemRepository;
    }
    @Override
    public ATMProcessor getNextAtmProcessor() {
        while (true){
            try {
                outputService.print(getMenuItemListMessage());
                String userInput = inputService.read();
                int menuItemId = Integer.parseInt(userInput);
                Optional<MenuItem> menuItem = menuItemRepository.getMenuItem(menuItemId);
                if (menuItem.isEmpty()){
                    throw new MenuItemNotExistException("Such option doesn't exist. id:" + menuItemId);
                }
                return menuItem.get().getAtmProcessor();
            } catch (RuntimeException exception){
                outputService.print("Incorrect input. Please, try again.");
            }
        }
    }

    private String getMenuItemListMessage(){
        StringBuilder builder = new StringBuilder();
        builder.append("Available options: \n");
        for (MenuItem menuItem: menuItemRepository.getMenuItemList()){
            builder.append(menuItemFormatter.format(menuItem)).append("\n");
        }
        builder.append("Please, enter digit of option");
        return builder.toString();
    }
}
