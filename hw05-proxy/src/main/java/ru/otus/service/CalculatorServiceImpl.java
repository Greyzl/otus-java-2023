package ru.otus.service;

import ru.otus.annotation.Log;

public class CalculatorServiceImpl implements CalculatorService {

    @Override
    @Log
    public long sum(long first, long second) {
        return first + second;
    }

    @Override
    public long multiply(long first, long second) {
        return first * second;
    }
}
