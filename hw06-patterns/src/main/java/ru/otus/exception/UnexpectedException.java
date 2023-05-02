package ru.otus.exception;

public class UnexpectedException extends RuntimeException{
    public UnexpectedException(String text){
        super(text);
    }
}
