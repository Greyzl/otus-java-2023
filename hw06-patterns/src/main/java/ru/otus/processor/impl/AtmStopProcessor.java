package ru.otus.processor.impl;

import ru.otus.processor.ATMProcessor;
import ru.otus.service.ApplicationStatusService;
import ru.otus.service.OutputService;

public class AtmStopProcessor implements ATMProcessor {

    private final ApplicationStatusService applicationStatusService;

    private final OutputService outputService;

    public AtmStopProcessor(ApplicationStatusService applicationStatusService,
                            OutputService outputService){
        this.applicationStatusService = applicationStatusService;
        this.outputService = outputService;
    }

    @Override
    public void process() {
        outputService.print("Thank you and goodbye!");
        applicationStatusService.stop();
    }
}
