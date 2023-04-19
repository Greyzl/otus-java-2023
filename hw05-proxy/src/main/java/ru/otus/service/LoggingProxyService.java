package ru.otus.service;

import ru.otus.aop.logging.LoggingHandler;

import java.lang.reflect.Proxy;

public class LoggingProxyService {

    public CalculatorService createProxy(CalculatorService calculatorService){
        LoggingHandler handler = new LoggingHandler(calculatorService);
        return (CalculatorService) Proxy.newProxyInstance(
                LoggingProxyService.class.getClassLoader(),
                new Class<?>[]{CalculatorService.class},handler);
    }
}
