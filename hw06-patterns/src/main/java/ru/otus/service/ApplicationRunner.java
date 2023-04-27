package ru.otus.service;

import ru.otus.entity.MenuItem;
import ru.otus.exception.UnexpectedException;
import ru.otus.processor.MenuProcessor;
import ru.otus.repository.MenuProcessorRepository;

public class ApplicationRunner {

    private final MenuService menuService;

    private final MenuProcessorRepository menuProcessorRepository;

    private final ApplicationStatusService applicationStatusService;

    private final OutputService outputService;


    public ApplicationRunner(MenuService menuService,
                             MenuProcessorRepository menuProcessorRepository,
                             ApplicationStatusService applicationStatusService,
                             OutputService outputService){
        this.menuService = menuService;
        this.menuProcessorRepository = menuProcessorRepository;
        this.applicationStatusService = applicationStatusService;
        this.outputService = outputService;
    }

    public void run(){
        outputService.print("Welcome!");
        while (applicationStatusService.isRunning()){
            try {
                MenuItem selectedMenuItem = menuService.getSelectedMenuItem();
                MenuProcessor selectedProcessor = menuProcessorRepository.getByMenuItem(selectedMenuItem);
                selectedProcessor.process();
            } catch (Exception e){
                throw new UnexpectedException("Something bad happened");
            }
        }
    }

}
