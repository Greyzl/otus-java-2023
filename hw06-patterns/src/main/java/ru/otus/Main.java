package ru.otus;

import ru.otus.entity.MenuItem;
import ru.otus.repository.MenuItemRepository;
import ru.otus.repository.impl.MenuItemRepositoryImpl;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;
import ru.otus.service.impl.ConsoleInputService;
import ru.otus.service.impl.ConsoleOutputService;

public class Main {
    public static void main(String[] args) {
        InputService inputService = new ConsoleInputService();

        OutputService outputService = new ConsoleOutputService();

        //ATMProcessor balanceStatusProcessor = new BalanceStatusProcessor(outputService);
        //MenuItem balanceStatusMenuItem = new MenuItem(1, "Balance", balanceStatusProcessor);

        MenuItemRepository menuItemRepository = new MenuItemRepositoryImpl();
        //menuItemRepository.addMenuItem(balanceStatusMenuItem);
    }
}