package ru.otus;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.enums.BanknoteType;
import ru.otus.processor.AtmProcessor;
import ru.otus.processor.impl.BalanceStatusProcessor;
import ru.otus.processor.impl.CashWithdrawProcessor;
import ru.otus.processor.impl.PopupProcessor;
import ru.otus.repository.BanknoteCellRepository;
import ru.otus.repository.impl.BanknoteCellRepositoryImpl;
import ru.otus.service.*;
import ru.otus.service.impl.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InputService inputService = new ConsoleInputService();
        OutputService outputService = new ConsoleOutputService();

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
        AtmProcessor balanceStatusProcessor = new BalanceStatusProcessor(banknoteCellService, outputService);
        DispenserService dispenserService = new DispenserServiceImpl(banknoteCellService);
        AtmProcessor cashWithdrawProcessor = new CashWithdrawProcessor(outputService, inputService,
                banknoteCellService ,dispenserService);

        AtmProcessor popupAtmProcessor = new PopupProcessor(inputService, outputService, banknoteCellService);


        List<AtmProcessor> processors = new ArrayList<>();
        processors.add(balanceStatusProcessor);
        processors.add(popupAtmProcessor);
        processors.add(cashWithdrawProcessor);

        ApplicationRunner applicationRunner = new ApplicationRunner(
                outputService,
                processors);
        applicationRunner.run();
    }
}