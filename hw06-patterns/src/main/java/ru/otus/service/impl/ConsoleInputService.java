package ru.otus.service.impl;

import ru.otus.service.InputService;

import java.util.Scanner;

public class ConsoleInputService implements InputService {

    Scanner scanner = new Scanner(System.in);
    @Override
    public String read() {
        return scanner.nextLine();
    }
}
