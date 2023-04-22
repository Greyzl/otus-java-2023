package ru.otus.service;

import ru.otus.entity.BanknoteType;

public interface BanknoteCellService {

    int getBanknoteCount(BanknoteType banknoteType);

    void addBanknotes(BanknoteType banknoteType, int banknoteCount);

    void withdrawBanknotes(BanknoteType banknoteType, int banknoteCount);
}
