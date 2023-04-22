package ru.otus.repository;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.BanknoteType;

public interface BanknoteCellRepository {

    void addBanknoteCell(BanknoteCell banknoteCell);

    BanknoteCell getBanknoteCell(BanknoteType banknoteType);
}
