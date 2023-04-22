package ru.otus;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.enums.BanknoteType;
import ru.otus.entity.MenuItem;
import ru.otus.entity.enums.Permission;
import ru.otus.processor.ATMProcessor;
import ru.otus.processor.impl.AtmStopProcessor;
import ru.otus.processor.impl.BalanceStatusProcessor;
import ru.otus.processor.impl.CashWithdrawProcessor;
import ru.otus.repository.AtmProcessorRepository;
import ru.otus.repository.BanknoteCellRepository;
import ru.otus.repository.PermissionMenuItemRepository;
import ru.otus.repository.impl.AtmProcessorRepositoryImpl;
import ru.otus.repository.impl.BanknoteCellRepositoryImpl;
import ru.otus.repository.impl.PermissionMenuItemRepositoryImpl;
import ru.otus.service.*;
import ru.otus.service.impl.*;

public class Main {
    public static void main(String[] args) {
        InputService inputService = new ConsoleInputService();
        OutputService outputService = new ConsoleOutputService();

        ApplicationStatusService applicationStatusService = new ApplicationStatusService();
        ATMProcessor atmStopProcessor = new AtmStopProcessor(applicationStatusService, outputService);

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
        ATMProcessor balanceStatusProcessor = new BalanceStatusProcessor(banknoteCellService, outputService);

        CashService cashService = new CashServiceImpl(banknoteCellService);
        PermissionMenuItemRepository permissionMenuItemRepository = new PermissionMenuItemRepositoryImpl();
        MenuItem grantedMenuItem = new MenuItem(1, "Yes");
        MenuItem deniedMenuItem = new MenuItem(2, "No");
        permissionMenuItemRepository.add(grantedMenuItem, Permission.GRANTED);
        permissionMenuItemRepository.add(deniedMenuItem, Permission.DENIED);
        PermissionService permissionService = new PermissionServiceImpl(permissionMenuItemRepository, outputService,inputService);
        ATMProcessor cashWithdrawProcessor = new CashWithdrawProcessor(outputService, inputService, cashService, permissionService);

        PermissionMenuItemRepository menuItemRepository = new PermissionMenuItemRepositoryImpl();

        MenuItem balanceStatusMenuItem = new MenuItem(1, "Balance");
        MenuItem cashWithdrawMenuItem = new MenuItem(2, "Withdraw");
        MenuItem atmStopMenuItem = new MenuItem(3, "Exit");

        AtmProcessorRepository atmProcessorRepository = new AtmProcessorRepositoryImpl();
        atmProcessorRepository.add(balanceStatusMenuItem, balanceStatusProcessor);
        atmProcessorRepository.add(cashWithdrawMenuItem, cashWithdrawProcessor);
        atmProcessorRepository.add(atmStopMenuItem, atmStopProcessor);

        AtmMenuService atmMenuService = new AtmMenuServiceImpl(
                outputService, inputService, atmProcessorRepository);

        ApplicationRunner applicationRunner = new ApplicationRunner(
                atmMenuService, applicationStatusService, outputService);
        applicationRunner.run();
    }
}