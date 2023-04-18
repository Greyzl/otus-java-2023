package ru.otus;

import ru.otus.logic.TestCalculator;
import ru.otus.logic.TestCalculatorNew;
import ru.otus.testframework.TestExecutor;

public class Main {
    public static void main(String[] args) {
        TestExecutor.run(TestCalculator.class);
    }
}