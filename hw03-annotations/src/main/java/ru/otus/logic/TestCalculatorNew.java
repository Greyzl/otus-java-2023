package ru.otus.logic;

import org.junit.jupiter.api.Assertions;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;
import ru.otus.entity.Calculator;

public class TestCalculatorNew {
    Calculator calculator;

    @Before
    private void initCalculator(){
        calculator = new Calculator();
    }

    private void clean(){
        calculator = null;
    }
    @Test
    private void testSum(){
        double expected = 15.0;
        calculator.sum(4);
        calculator.sum(8);
        calculator.sum(2);
        Assertions.assertEquals(15,calculator.getResult());
    }

    @Test
    private void testMultiply(){
        calculator .multiply(5);
        System.out.println(calculator.getResult());
        Assertions.assertEquals(0.0,calculator.getResult());
    }



}
