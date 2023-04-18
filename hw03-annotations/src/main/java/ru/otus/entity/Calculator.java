package ru.otus.entity;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Calculator {
    private Double result = 0.0;
    private final Deque<Double> prevResults  = new LinkedList<>();


    public double getResult() {
        return result;
    }
    public void revert(){
        result  = prevResults.pollLast();
    }

    public Double sum(double input){
        prevResults.add(result);
        return  result += input;
    }

    public Double multiply(double input){
        prevResults.add(result);
        return result *= input;
    }
}
