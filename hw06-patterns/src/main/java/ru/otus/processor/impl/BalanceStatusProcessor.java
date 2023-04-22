package ru.otus.processor.impl;

import ru.otus.processor.ATMProcessor;
import ru.otus.service.OutputService;

public class BalanceStatusProcessor implements ATMProcessor {

    private final OutputService outputService;


    public BalanceStatusProcessor(OutputService outputService){
        this.outputService = outputService;
    }

    @Override
    public void process() {

    }
}
