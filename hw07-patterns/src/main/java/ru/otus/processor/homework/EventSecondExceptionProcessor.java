package ru.otus.processor.homework;

import ru.otus.exception.EvenSecondException;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.service.DateTimeService;

import java.time.LocalDateTime;

public class EventSecondExceptionProcessor implements Processor {
    private final DateTimeService dateTimeService;

    public EventSecondExceptionProcessor(DateTimeService dateTimeService){
        this.dateTimeService = dateTimeService;
    }
    @Override
    public Message process(Message message) {
        LocalDateTime now = dateTimeService.now();
        if (now.getSecond() % 2 == 0){
            throw new EvenSecondException();
        }
        return message;
    }
}
