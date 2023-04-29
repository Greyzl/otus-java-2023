package ru.otus.service.impl;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.BanknotePack;
import ru.otus.entity.enums.BanknoteType;
import ru.otus.exception.NotEnoughBanknotesException;
import ru.otus.repository.BanknoteCellRepository;
import ru.otus.service.BanknoteCellService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BanknoteCellServiceImpl implements BanknoteCellService {

    private final BanknoteCellRepository banknoteCellRepository;

    public BanknoteCellServiceImpl(BanknoteCellRepository banknoteCellRepository){
        this.banknoteCellRepository = banknoteCellRepository;
    }

    @Override
    public void addBanknoteCount(BanknoteType banknoteType, int banknoteCount) {
        var banknoteCell =  banknoteCellRepository.get(banknoteType);
        banknoteCell.addBanknoteCount(banknoteCount);
        banknoteCellRepository.save(banknoteCell);
    }

    @Override
    public void withdrawBanknoteCount(BanknoteType banknoteType, int banknoteCount) {
        var banknoteCell =  banknoteCellRepository.get(banknoteType);
        if (banknoteCell.getBanknoteCount() < banknoteCount){
            throw new NotEnoughBanknotesException("Banknote type:" + banknoteType);
        }
        banknoteCell.withdrawBanknoteCount(banknoteCount);
        banknoteCellRepository.save(banknoteCell);
    }

    @Override
    public int getAvailableCashAmount() {
        Optional<Integer> mayBeAvailableCashAmount = banknoteCellRepository.getAll().stream().map(BanknoteCell::getTotal)
                .reduce(Integer::sum);
        return mayBeAvailableCashAmount.orElse(0);
    }

    public List<BanknotePack> getAvailableBanknotePacks(){
        List<BanknoteCell> banknoteCellList = banknoteCellRepository.getAll();
        return banknoteCellList.stream()
                .filter(banknoteCell -> !banknoteCell.isEmpty())
                .map(BanknoteCell-> new BanknotePack(BanknoteCell.getBanknoteType(), BanknoteCell.getBanknoteCount()))
                .collect(Collectors.toList());
    }
}
