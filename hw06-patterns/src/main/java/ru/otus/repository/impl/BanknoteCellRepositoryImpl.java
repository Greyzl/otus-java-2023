package ru.otus.repository.impl;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.BanknoteType;
import ru.otus.repository.BanknoteCellRepository;

import java.util.HashMap;
import java.util.Map;

public class BanknoteCellRepositoryImpl implements BanknoteCellRepository {

    Map<BanknoteType, BanknoteCell> banknoteCellMap = new HashMap<>();

    @Override
    public void addBanknoteCell(BanknoteCell banknoteCell) {
        banknoteCellMap.put(banknoteCell.getBanknoteType(),banknoteCell);
    }

    @Override
    public BanknoteCell getBanknoteCell(BanknoteType banknoteType) {
        return banknoteCellMap.get(banknoteType);
    }
}
