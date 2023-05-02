package ru.otus.service.impl;

import ru.otus.service.DateTimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeServiceImpl implements DateTimeService {

    public LocalDateTime now(){
        return LocalDateTime.now();
    }
}
