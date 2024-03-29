package ru.otus.processor.impl;

import ru.otus.formatter.AtmBalanceMessageFormatter;
import ru.otus.processor.AtmProcessor;
import ru.otus.service.BanknoteCellService;
import ru.otus.service.OutputService;

public class BalanceStatusProcessor implements AtmProcessor {

    private final BanknoteCellService banknoteCellService;

    private final AtmBalanceMessageFormatter messageFormatter = new AtmBalanceMessageFormatter();

    private final OutputService outputService;

    public BalanceStatusProcessor(BanknoteCellService banknoteCellService, OutputService outputService){
        this.banknoteCellService = banknoteCellService;
        this.outputService = outputService;
    }

    @Override
    public void process() {
        int balance = banknoteCellService.getAvailableCashAmount();
        String formattedMessage = messageFormatter.format(balance);
        outputService.print(formattedMessage);
    }
}
