package ru.otus.repository.impl;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.enums.BanknoteType;
import ru.otus.repository.BanknoteCellRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BanknoteCellRepositoryImpl implements BanknoteCellRepository {

    Map<BanknoteType, BanknoteCell> banknoteCellMap = new HashMap<>();

    @Override
    public void save(BanknoteCell banknoteCell) {
        banknoteCellMap.put(banknoteCell.getBanknoteType(),banknoteCell);
    }

    @Override
    public BanknoteCell get(BanknoteType banknoteType) {
        BanknoteCell banknoteCell = banknoteCellMap.get(banknoteType);
        return new BanknoteCell(banknoteCell.getBanknoteType(),
                banknoteCell.getBanknoteCount());
    }

    @Override
    public List<BanknoteCell> getAll() {
        return banknoteCellMap.values().stream()
                .map(banknoteCell -> new BanknoteCell(banknoteCell.getBanknoteType(),
                        banknoteCell.getBanknoteCount())).collect(Collectors.toList());
    }
}
