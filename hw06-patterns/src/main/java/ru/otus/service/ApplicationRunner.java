package ru.otus.service;

import ru.otus.exception.UnexpectedException;
import ru.otus.processor.ATMProcessor;

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
