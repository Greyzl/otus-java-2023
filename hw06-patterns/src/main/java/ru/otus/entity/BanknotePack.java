package ru.otus.entity;

import ru.otus.entity.enums.BanknoteType;

import java.util.Objects;

public class BanknotePack {
    private final BanknoteType banknoteType;

    private int banknoteCount;

    private int total;

    public BanknotePack(BanknoteType banknoteType, int banknoteCount){
        this.banknoteType = banknoteType;
        this.banknoteCount = banknoteCount;
        this.total = banknoteType.getBanknoteValue() * banknoteCount;
    }

    public BanknoteType getBanknoteType() {
        return banknoteType;
    }

    public int getBanknoteCount() {
        return banknoteCount;
    }

    public void addBanknoteCount(int banknoteCount){
        this.banknoteCount += banknoteCount;
        this.total += banknoteCount * banknoteType.getBanknoteValue();
    }

    public void withdrawBanknoteCount(int banknoteCount){
        this.banknoteCount -= banknoteCount;
        this.total -= banknoteCount * banknoteType.getBanknoteValue();
    }

    public int getTotal(){
        return total;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BanknotePack that)) return false;
        return getBanknoteType() == that.getBanknoteType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBanknoteType());
    }
}
