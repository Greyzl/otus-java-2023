package ru.otus.service;

public class ApplicationRunner {

    public void run(){
        CalculatorServiceFactory serviceFactory = new CalculatorServiceFactory();
        CalculatorService calculatorService = serviceFactory.create();

        long sumResult = calculatorService.sum(5, 8);
        long multiplyResult = calculatorService.multiply(4,5);
        System.out.println("Sum result: " + sumResult);
        System.out.println("Multiply result: " + multiplyResult);
    }
}
