package ru.otus.entity;

import ru.otus.entity.enums.BanknoteType;

import java.util.*;

public class Cash {

    private final Map<BanknoteType,BanknotePack> cashPacks = new HashMap<>();

    private int total;

    public void addBanknoteCount(BanknoteType banknoteType, int banknoteCount){
        Optional<BanknotePack> mayBeBanknotePack = Optional.ofNullable(cashPacks.get(banknoteType));
        if (mayBeBanknotePack.isPresent()){
            mayBeBanknotePack.get().addBanknoteCount(banknoteCount);
        } else {
            BanknotePack banknotePack = new BanknotePack(banknoteType,banknoteCount);
            cashPacks.put(banknoteType, banknotePack);
        }
        total += banknoteType.getBanknoteValue() * banknoteCount;
    }

    public List<BanknotePack> getBanknotePacks(){
        return cashPacks.values().stream().toList();
    }

    public int getTotal(){
        return total;
    }
}
