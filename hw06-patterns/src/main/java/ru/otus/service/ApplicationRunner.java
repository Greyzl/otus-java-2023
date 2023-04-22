package ru.otus.service;

import ru.otus.entity.MenuItem;
import ru.otus.exception.UnexpectedException;
import ru.otus.processor.ATMProcessor;
import ru.otus.processor.impl.BalanceStatusProcessor;
import ru.otus.repository.MenuItemRepository;
import ru.otus.repository.impl.MenuItemRepositoryImpl;
import ru.otus.service.impl.ConsoleInputService;
import ru.otus.service.impl.ConsoleOutputService;

public class ApplicationRunner {

    private final AtmMenuService atmMenuService;

    private final ApplicationStatusService applicationStatusService;

    private final OutputService outputService;

    public ApplicationRunner(AtmMenuService atmMenuService,
                             ApplicationStatusService applicationStatusService,
                             OutputService outputService){
        this.atmMenuService = atmMenuService;
        this.applicationStatusService = applicationStatusService;
        this.outputService = outputService;
    }

    public void run(){
        outputService.print("Welcome!");
        while (applicationStatusService.isRunning()){
            try {
                ATMProcessor processor = atmMenuService.getNextAtmProcessor();
                processor.process();
            } catch (Exception e){
                throw new UnexpectedException("Something bad happened");
            }
        }
    }

}
