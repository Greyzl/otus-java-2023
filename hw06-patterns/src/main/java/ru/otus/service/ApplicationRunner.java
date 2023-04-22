package ru.otus.service;

import ru.otus.entity.MenuItem;
import ru.otus.processor.ATMProcessor;
import ru.otus.processor.impl.BalanceStatusProcessor;
import ru.otus.repository.MenuItemRepository;
import ru.otus.repository.impl.MenuItemRepositoryImpl;
import ru.otus.service.impl.ConsoleInputService;
import ru.otus.service.impl.ConsoleOutputService;

public class ApplicationRunner {

    public void run(){
        InputService inputService = new ConsoleInputService();

        OutputService outputService = new ConsoleOutputService();

        ATMProcessor balanceStatusProcessor = new BalanceStatusProcessor(outputService);
        MenuItem balanceStatusMenuItem = new MenuItem(1, "Balance", balanceStatusProcessor);

        MenuItemRepository menuItemRepository = new MenuItemRepositoryImpl();
        menuItemRepository.addMenuItem(balanceStatusMenuItem);
    }

}
