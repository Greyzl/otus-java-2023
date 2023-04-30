package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.exception.EvenSecondException;
import ru.otus.model.Message;
import ru.otus.service.DateTimeService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventSecondExceptionProcessorTest {


    @Test
    void evenDateTimeProcessTest() {
        DateTimeService eventDateTimeService = () -> LocalDateTime.of(2023, 4, 1,12,0,2 );
        EventSecondExceptionProcessor eventSecondExceptionProcessor =
                new EventSecondExceptionProcessor(eventDateTimeService);
        Message.Builder builder = new Message.Builder(1L);
        Message message = builder.build();
        assertThrowsExactly(EvenSecondException.class, () -> eventSecondExceptionProcessor.process(message));
    }

    @Test
    void oddDateTimeProcessTest() {
        DateTimeService eventDateTimeService = () -> LocalDateTime.of(2023, 4, 1,12,0,3 );
        EventSecondExceptionProcessor eventSecondExceptionProcessor =
                new EventSecondExceptionProcessor(eventDateTimeService);
        Message.Builder builder = new Message.Builder(1L);
        Message message = builder.build();
        assertDoesNotThrow( () -> eventSecondExceptionProcessor.process(message));
    }
}