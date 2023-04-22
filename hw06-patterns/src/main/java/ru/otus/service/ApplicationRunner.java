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

    public ApplicationRunner(AtmMenuService atmMenuService,
                             ApplicationStatusService applicationStatusService){
        this.atmMenuService = atmMenuService;
        this.applicationStatusService = applicationStatusService;
    }

    public void run(){
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
