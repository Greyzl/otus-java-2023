package ru.otus.service;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.BanknoteType;

import java.util.List;

public interface BanknoteCellService {

    int getBanknoteCount(BanknoteType banknoteType);

    void addToBalance(List<BanknoteCell> banknoteCellList);

    void withdraw(List<BanknoteCell> requestedCash);

    List<BanknoteCell> getPossibleWithdraw(int requestedCash);

    int getAvailableCashAmount();


}
