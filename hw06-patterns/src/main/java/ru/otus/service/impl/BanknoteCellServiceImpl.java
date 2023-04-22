package ru.otus.service.impl;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.BanknoteType;
import ru.otus.exception.NotEnoughBanknotesException;
import ru.otus.repository.BanknoteCellRepository;
import ru.otus.service.BanknoteCellService;

public class BanknoteCellServiceImpl implements BanknoteCellService {

    private final BanknoteCellRepository banknoteCellRepository;

    public BanknoteCellServiceImpl(BanknoteCellRepository banknoteCellRepository){
        this.banknoteCellRepository = banknoteCellRepository;
    }

    @Override
    public int getBanknoteCount(BanknoteType banknoteType) {
        BanknoteCell banknoteCell = banknoteCellRepository.getBanknoteCell(banknoteType);
        return banknoteCell.getBanknoteCount();
    }

    @Override
    public void addBanknotes(BanknoteType banknoteType, int banknoteCount) {
        BanknoteCell banknoteCell = banknoteCellRepository.getBanknoteCell(banknoteType);
        int currentBanknoteCount = banknoteCell.getBanknoteCount();
        banknoteCell.setBanknoteCount(currentBanknoteCount + banknoteCount);
    }

    @Override
    public void withdrawBanknotes(BanknoteType banknoteType, int banknoteCount) {
        BanknoteCell banknoteCell = banknoteCellRepository.getBanknoteCell(banknoteType);
        int currentBanknoteCount = banknoteCell.getBanknoteCount();
        int targetBanknoteCount = currentBanknoteCount - banknoteCount;
        if (targetBanknoteCount < 0) {
            throw new NotEnoughBanknotesException("Target banknotes count below zero");
        }
        banknoteCell.setBanknoteCount(targetBanknoteCount);
    }
}
