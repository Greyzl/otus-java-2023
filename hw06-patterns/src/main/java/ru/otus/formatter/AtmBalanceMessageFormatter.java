package ru.otus.formatter;

public class AtmBalanceMessageFormatter {

    public String format(int balance){
        return String.format("Your balance is: %d",balance);
    }
}
