package ru.otus.service;

import ru.otus.entity.ApplicationStatus;

public class ApplicationStatusService {

    private ApplicationStatus applicationStatus = ApplicationStatus.RUNNING;

    public void stop(){
        applicationStatus = ApplicationStatus.STOPPED;
    }

    public Boolean isRunning(){
        return applicationStatus == ApplicationStatus.RUNNING;
    }
}
