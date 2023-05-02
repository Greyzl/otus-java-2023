package ru.otus.service.impl;

import ru.otus.service.OutputService;

import java.io.PrintStream;

public class ConsoleOutputService implements OutputService {

    private final PrintStream printStream = System.out;

    @Override
    public void print(String text) {
        System.out.println(text);
    }
}
