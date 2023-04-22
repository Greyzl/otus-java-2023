package ru.otus.processor.impl;

import ru.otus.processor.ATMProcessor;
import ru.otus.service.ApplicationStatusService;

public class AtmStopProcessor implements ATMProcessor {

    private final ApplicationStatusService applicationStatusService;

    public AtmStopProcessor(ApplicationStatusService applicationStatusService){
        this.applicationStatusService = applicationStatusService;
    }

    @Override
    public void process() {
        applicationStatusService.stop();
    }
}
