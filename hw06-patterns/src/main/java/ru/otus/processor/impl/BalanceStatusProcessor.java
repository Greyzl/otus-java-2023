package ru.otus.processor.impl;

import ru.otus.entity.BanknoteType;
import ru.otus.formatter.AtmBalanceMessageFormatter;
import ru.otus.processor.ATMProcessor;
import ru.otus.service.BanknoteCellService;
import ru.otus.service.OutputService;

public class BalanceStatusProcessor implements ATMProcessor {

    private final BanknoteCellService banknoteCellService;

    private final AtmBalanceMessageFormatter messageFormatter = new AtmBalanceMessageFormatter();

    private final OutputService outputService;

    public BalanceStatusProcessor(BanknoteCellService banknoteCellService, OutputService outputService){
        this.banknoteCellService = banknoteCellService;
        this.outputService = outputService;
    }

    @Override
    public void process() {
        int balance = 0;
        for (BanknoteType banknoteType: BanknoteType.values())
            balance += banknoteCellService.getBanknoteCount(banknoteType) * banknoteType.getBanknoteValue();
        String formattedMessage = messageFormatter.format(balance);
        outputService.print(formattedMessage);
    }
}
