package ru.otus.entity;

import java.util.Objects;

public class BanknotePack {
    private final BanknoteType banknoteType;

    private int banknoteCount;

    public BanknotePack(BanknoteType banknoteType, int banknoteCount){
        this.banknoteType = banknoteType;
        this.banknoteCount = banknoteCount;
    }

    public BanknoteType getBanknoteType() {
        return banknoteType;
    }

    public int getBanknoteCount() {
        return banknoteCount;
    }

    public void setBanknoteCount(int banknoteCount){
        this.banknoteCount = banknoteCount;
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
