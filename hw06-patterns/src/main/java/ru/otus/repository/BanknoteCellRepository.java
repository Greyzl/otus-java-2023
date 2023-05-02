package ru.otus.repository;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.enums.BanknoteType;

import java.util.List;

public interface BanknoteCellRepository {

    void save(BanknoteCell banknoteCell);

    BanknoteCell get(BanknoteType banknoteType);

    List<BanknoteCell> getAll();
}
