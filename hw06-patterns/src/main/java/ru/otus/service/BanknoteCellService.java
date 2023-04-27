package ru.otus.service;

import ru.otus.entity.BanknotePack;
import ru.otus.entity.enums.BanknoteType;

import java.util.List;

public interface BanknoteCellService {

    void addBanknoteCount(BanknoteType banknoteType, int banknoteCount);

    void withdrawBanknoteCount(BanknoteType banknoteType, int banknoteCount);

    List<BanknotePack> getAvailableBanknotePacks();

    int getAvailableCashAmount();


}
