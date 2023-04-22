package ru.otus.service;

import ru.otus.processor.ATMProcessor;

public interface AtmMenuService {

    ATMProcessor getNextAtmProcessor();
}
