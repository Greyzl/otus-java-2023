package ru.otus.entity;

public class BanknoteCell {

    private final BanknoteType banknoteType;

    private int banknoteCount;

    public BanknoteCell(BanknoteType banknoteType, int banknoteCount){
        this.banknoteType = banknoteType;
        this.banknoteCount = banknoteCount;
    }

    public BanknoteType getBanknoteType() {
        return banknoteType;
    }

    public int getBanknoteCount() {
        return banknoteCount;
    }

    public void setBanknoteCount(int banknoteCount) {
        this.banknoteCount = banknoteCount;
    }
}
