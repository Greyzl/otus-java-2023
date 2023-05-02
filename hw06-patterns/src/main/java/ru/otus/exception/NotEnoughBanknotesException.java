package ru.otus.exception;

public class NotEnoughBanknotesException extends RuntimeException{
    public NotEnoughBanknotesException(String exceptionText) {
        super(exceptionText);
    }
}
