package ru.otus.service;

import ru.otus.processor.AtmProcessor;

import java.util.List;

public class ApplicationRunner {

    private final OutputService outputService;

    private final List<AtmProcessor> processors;


    public ApplicationRunner(OutputService outputService,
                             List<AtmProcessor> processors){
        this.outputService = outputService;
        this.processors = processors;
    }

    public void run(){
        outputService.print("Welcome!");
        for (AtmProcessor atmProcessor: processors){
            atmProcessor.process();
        }
    }

}
