package ru.otus.entity;

import ru.otus.entity.enums.BanknoteType;

public class BanknoteCell{

    private final BanknotePack banknotePack;

    public BanknoteCell(BanknoteType banknoteType, int banknoteCount){
        this.banknotePack = new BanknotePack(banknoteType, banknoteCount);
    }

    public BanknoteType getBanknoteType() {
        return banknotePack.getBanknoteType();
    }

    public int getBanknoteCount() {
        return banknotePack.getBanknoteCount();
    }

    public void addBanknoteCount(int banknoteCount) {
        banknotePack.addBanknoteCount(banknoteCount);
    }

    public void withdrawBanknoteCount(int banknoteCount) {
        banknotePack.withdrawBanknoteCount(banknoteCount);
    }

    public int getTotal(){
        return banknotePack.getTotal();
    }

    public Boolean isEmpty(){
        return banknotePack.getBanknoteCount() == 0;
    }
}
