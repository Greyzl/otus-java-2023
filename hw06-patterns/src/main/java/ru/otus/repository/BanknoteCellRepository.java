package ru.otus.repository;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.BanknoteType;

import java.util.List;

public interface BanknoteCellRepository {

    void add(BanknoteCell banknoteCell);

    BanknoteCell get(BanknoteType banknoteType);

    List<BanknoteCell> getAll();
}
