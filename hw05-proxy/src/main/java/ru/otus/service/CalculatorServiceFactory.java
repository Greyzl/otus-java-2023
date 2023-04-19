package ru.otus.service;

public class CalculatorServiceFactory {
    private LoggingProxyService loggingProxyService;

    public  CalculatorService create(){
        if (loggingProxyService == null){
            loggingProxyService = new LoggingProxyService();
        }
        CalculatorService calculatorService = new CalculatorServiceImpl();
        return loggingProxyService.createProxy(calculatorService);
    }
}
