package ru.otus;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.enums.BanknoteType;
import ru.otus.entity.MenuItem;
import ru.otus.entity.enums.Permission;
import ru.otus.processor.MenuProcessor;
import ru.otus.processor.impl.AtmStopProcessor;
import ru.otus.processor.impl.BalanceStatusProcessor;
import ru.otus.processor.impl.CashWithdrawProcessor;
import ru.otus.processor.impl.PopupProcessor;
import ru.otus.repository.MenuItemRepository;
import ru.otus.repository.BanknoteCellRepository;
import ru.otus.repository.MenuProcessorRepository;
import ru.otus.repository.MenuPermissionRepository;
import ru.otus.repository.impl.MenuItemRepositoryImpl;
import ru.otus.repository.impl.BanknoteCellRepositoryImpl;
import ru.otus.repository.impl.MenuProcessorRepositoryImpl;
import ru.otus.repository.impl.MenuPermissionRepositoryImpl;
import ru.otus.service.*;
import ru.otus.service.impl.*;

public class Main {
    public static void main(String[] args) {
        InputService inputService = new ConsoleInputService();
        OutputService outputService = new ConsoleOutputService();

        ApplicationStatusService applicationStatusService = new ApplicationStatusService();
        MenuProcessor atmStopProcessor = new AtmStopProcessor(applicationStatusService, outputService);

        BanknoteCellRepository banknoteCellRepository = new BanknoteCellRepositoryImpl();
        BanknoteCell onesBanknotesCell = new BanknoteCell(BanknoteType.ONES, 10);
        BanknoteCell tenBanknoteCell = new BanknoteCell(BanknoteType.TEN, 100);
        BanknoteCell fiftyBanknoteCell = new BanknoteCell(BanknoteType.FIFTY, 50);
        BanknoteCell hundredBanknoteCell = new BanknoteCell(BanknoteType.HUNDRED, 50);
        BanknoteCell fiveHundredBanknoteCell = new BanknoteCell(BanknoteType.FIVE_HUNDRED, 20);
        BanknoteCell thousandBanknoteCell = new BanknoteCell(BanknoteType.THOUSAND, 10);

        banknoteCellRepository.save(onesBanknotesCell);
        banknoteCellRepository.save(tenBanknoteCell);
        banknoteCellRepository.save(fiftyBanknoteCell);
        banknoteCellRepository.save(hundredBanknoteCell);
        banknoteCellRepository.save(fiveHundredBanknoteCell);
        banknoteCellRepository.save(thousandBanknoteCell);

        BanknoteCellService banknoteCellService = new BanknoteCellServiceImpl(banknoteCellRepository);
        MenuProcessor balanceStatusProcessor = new BalanceStatusProcessor(banknoteCellService, outputService);
        DispenserService dispenserService = new DispenserServiceImpl(banknoteCellService);


        MenuItemRepository permissionMenuItemRepository = new MenuItemRepositoryImpl();
        MenuItem grantedMenuItem = new MenuItem(1, "Yes");
        MenuItem deniedMenuItem = new MenuItem(2, "No");
        permissionMenuItemRepository.add(grantedMenuItem);
        permissionMenuItemRepository.add(deniedMenuItem);

        MenuPermissionRepository menuPermissionRepository = new MenuPermissionRepositoryImpl();
        menuPermissionRepository.add(grantedMenuItem, Permission.GRANTED);
        menuPermissionRepository.add(deniedMenuItem, Permission.DENIED);

        PermissionService permissionService = new PermissionServiceImpl(permissionMenuItemRepository,
                menuPermissionRepository, outputService,inputService);
        MenuProcessor cashWithdrawProcessor = new CashWithdrawProcessor(outputService, inputService,
                banknoteCellService ,dispenserService, permissionService);

        MenuProcessor popupMenuProcessor = new PopupProcessor(inputService, outputService, banknoteCellService);


        MenuItem balanceStatusMenuItem = new MenuItem(1, "Balance");
        MenuItem cashWithdrawMenuItem = new MenuItem(2, "Withdraw");
        MenuItem popupMenuItem = new MenuItem(3, "Popup");
        MenuItem atmStopMenuItem = new MenuItem(4, "Exit");

        MenuItemRepository mainMenuItemsRepository = new MenuItemRepositoryImpl();
        mainMenuItemsRepository.add(balanceStatusMenuItem);
        mainMenuItemsRepository.add(cashWithdrawMenuItem);
        mainMenuItemsRepository.add(popupMenuItem);
        mainMenuItemsRepository.add(atmStopMenuItem);

        MenuService mainMenuService = new MenuServiceImpl(
                outputService, inputService, mainMenuItemsRepository);

        MenuProcessorRepository mainMenuProcessorRepository = new MenuProcessorRepositoryImpl();
        mainMenuProcessorRepository.add(balanceStatusMenuItem, balanceStatusProcessor);
        mainMenuProcessorRepository.add(cashWithdrawMenuItem, cashWithdrawProcessor);
        mainMenuProcessorRepository.add(popupMenuItem, popupMenuProcessor);
        mainMenuProcessorRepository.add(atmStopMenuItem, atmStopProcessor);


        ApplicationRunner applicationRunner = new ApplicationRunner(
                mainMenuService, mainMenuProcessorRepository, applicationStatusService, outputService);
        applicationRunner.run();
    }
}