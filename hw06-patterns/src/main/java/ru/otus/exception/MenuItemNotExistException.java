package ru.otus.exception;

public class MenuItemNotExistException extends RuntimeException{
    public MenuItemNotExistException(String text){
        super(text);
    }
}
