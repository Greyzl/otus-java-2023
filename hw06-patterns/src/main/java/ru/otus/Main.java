package ru.otus;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.BanknoteType;
import ru.otus.entity.MenuItem;
import ru.otus.processor.ATMProcessor;
import ru.otus.processor.impl.AtmStopProcessor;
import ru.otus.processor.impl.BalanceStatusProcessor;
import ru.otus.processor.impl.CashWithdrawProcessor;
import ru.otus.repository.BanknoteCellRepository;
import ru.otus.repository.MenuItemRepository;
import ru.otus.repository.impl.BanknoteCellRepositoryImpl;
import ru.otus.repository.impl.MenuItemRepositoryImpl;
import ru.otus.service.*;
import ru.otus.service.impl.AtmMenuServiceImpl;
import ru.otus.service.impl.BanknoteCellServiceImpl;
import ru.otus.service.impl.ConsoleInputService;
import ru.otus.service.impl.ConsoleOutputService;

public class Main {
    public static void main(String[] args) {
        InputService inputService = new ConsoleInputService();
        OutputService outputService = new ConsoleOutputService();

        ApplicationStatusService applicationStatusService = new ApplicationStatusService();
        ATMProcessor atmStopProcessor = new AtmStopProcessor(applicationStatusService, outputService);

        BanknoteCellRepository banknoteCellRepository = new BanknoteCellRepositoryImpl();
        BanknoteCell onesBanknotesCell = new BanknoteCell(BanknoteType.ONES, 100);
        BanknoteCell tenBanknoteCell = new BanknoteCell(BanknoteType.TEN, 100);
        BanknoteCell fiftyBanknoteCell = new BanknoteCell(BanknoteType.FIFTY, 50);
        BanknoteCell hundredBanknoteCell = new BanknoteCell(BanknoteType.HUNDRED, 50);
        BanknoteCell fiveHundredBanknoteCell = new BanknoteCell(BanknoteType.FIVE_HUNDRED, 20);
        BanknoteCell thousandBanknoteCell = new BanknoteCell(BanknoteType.THOUSAND, 10);

        banknoteCellRepository.add(onesBanknotesCell);
        banknoteCellRepository.add(tenBanknoteCell);
        banknoteCellRepository.add(fiftyBanknoteCell);
        banknoteCellRepository.add(hundredBanknoteCell);
        banknoteCellRepository.add(fiveHundredBanknoteCell);
        banknoteCellRepository.add(thousandBanknoteCell);

        BanknoteCellService banknoteCellService = new BanknoteCellServiceImpl(banknoteCellRepository);
        ATMProcessor balanceStatusProcessor = new BalanceStatusProcessor(banknoteCellService, outputService);
        ATMProcessor cashWithdrawProcessor = new CashWithdrawProcessor(outputService, inputService, banknoteCellService);

        MenuItemRepository menuItemRepository = new MenuItemRepositoryImpl();

        MenuItem balanceStatusMenuItem = new MenuItem(1, "Balance", balanceStatusProcessor);
        MenuItem cashWithdrawMenuItem = new MenuItem(2, "Withdraw" , cashWithdrawProcessor);
        MenuItem atmStopMenuItem = new MenuItem(3, "Exit", atmStopProcessor);

        menuItemRepository.addMenuItem(balanceStatusMenuItem);
        menuItemRepository.addMenuItem(cashWithdrawMenuItem);
        menuItemRepository.addMenuItem(atmStopMenuItem);
        AtmMenuService atmMenuService = new AtmMenuServiceImpl(
                outputService, inputService, menuItemRepository);

        ApplicationRunner applicationRunner = new ApplicationRunner(
                atmMenuService, applicationStatusService, outputService);
        applicationRunner.run();
    }
}