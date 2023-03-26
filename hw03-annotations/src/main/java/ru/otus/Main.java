package ru.otus;

import ru.otus.logic.TestCalculator;
import ru.otus.testframework.testExecutor;

public class Main {
    public static void main(String[] args) {
        testExecutor.run(TestCalculator.class);
    }
}