package ru.otus.entity;

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

    public void setBanknoteCount(int banknoteCount) {
        banknotePack.setBanknoteCount(banknoteCount);
    }

    public Boolean isEmpty(){
        return banknotePack.getBanknoteCount() == 0;
    }
}
