package ru.otus.exception;

public class IncorrectRequestedAmount extends RuntimeException{
    public IncorrectRequestedAmount(Exception exception){
        super(exception);
    }
    public IncorrectRequestedAmount(String text){
        super(text);
    }
}
