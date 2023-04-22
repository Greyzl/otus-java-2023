package ru.otus.entity.enums;

public enum BanknoteType {
    ONES(1),
    TEN(10),
    FIFTY(50),
    HUNDRED(100),
    FIVE_HUNDRED(500),
    THOUSAND(1000);

    private final int banknoteValue;

    BanknoteType(int banknoteValue){
        this.banknoteValue = banknoteValue;
    }

    public int getBanknoteValue(){
        return banknoteValue;
    };
}
