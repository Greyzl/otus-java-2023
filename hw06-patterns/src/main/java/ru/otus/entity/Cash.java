package ru.otus.entity;

import java.util.*;

public class Cash {

    private final Map<BanknoteType,BanknotePack> cashPacks = new HashMap();

    private int total;

    public void addBanknoteCount(BanknoteType banknoteType, int banknoteCount){
        Optional<BanknotePack> mayBeBanknotePack = Optional.ofNullable(cashPacks.get(banknoteType));
        if (mayBeBanknotePack.isPresent()){
            mayBeBanknotePack.get().setBanknoteCount(banknoteCount);
        } else {
            BanknotePack banknotePack = new BanknotePack(banknoteType,banknoteCount);
            cashPacks.put(banknoteType, banknotePack);
        }
        total += banknoteType.getBanknoteValue() * banknoteCount;
    }


    public int getTotal(){
        return total;
    }
}
