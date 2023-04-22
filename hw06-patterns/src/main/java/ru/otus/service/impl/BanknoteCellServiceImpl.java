package ru.otus.service.impl;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.BanknoteType;
import ru.otus.exception.NotEnoughBanknotesException;
import ru.otus.repository.BanknoteCellRepository;
import ru.otus.service.BanknoteCellService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BanknoteCellServiceImpl implements BanknoteCellService {

    private final BanknoteCellRepository banknoteCellRepository;

    public BanknoteCellServiceImpl(BanknoteCellRepository banknoteCellRepository){
        this.banknoteCellRepository = banknoteCellRepository;
    }

    @Override
    public int getBanknoteCount(BanknoteType banknoteType) {
        BanknoteCell banknoteCell = banknoteCellRepository.get(banknoteType);
        return banknoteCell.getBanknoteCount();
    }

    @Override
    public void addToBalance(List<BanknoteCell> banknoteCellList) {
//        BanknoteCell banknoteCell = banknoteCellRepository.get(banknoteType);
//        int currentBanknoteCount = banknoteCell.getBanknoteCount();
//        banknoteCell.setBanknoteCount(currentBanknoteCount + banknoteCount);
    }

    @Override
    public void withdraw(List<BanknoteCell> requestedBanknotes) {
        for (BanknoteCell requestedBanknote: requestedBanknotes){
            BanknoteType requestedBanknoteType = requestedBanknote.getBanknoteType();
            BanknoteCell availableBanknoteCell = banknoteCellRepository.get(requestedBanknoteType);
            int remainsBanknoteCount = availableBanknoteCell.getBanknoteCount() - requestedBanknote.getBanknoteCount();
            availableBanknoteCell.setBanknoteCount(remainsBanknoteCount);
        }
    }

    public List<BanknoteCell> getPossibleWithdraw(int requestedCash){
        List<BanknoteCell> availableBanknotesCells = getAvailableBanknoteCells();
        List<BanknoteCell> banknotesToWithdraw = new ArrayList<>();
        for (BanknoteCell availableBanknoteCell: availableBanknotesCells){
            BanknoteType banknoteType = availableBanknoteCell.getBanknoteType();
            int availableBanknoteCount = availableBanknoteCell.getBanknoteCount();
            int  requestedBanknotesCount = requestedCash / banknoteType.getBanknoteValue();
            if (requestedBanknotesCount == 0){
                continue;
            }
            int banknotesCountToWithdraw = 0;
            if (requestedBanknotesCount >= availableBanknoteCount){
                requestedCash -= availableBanknoteCount * banknoteType.getBanknoteValue();
                banknotesCountToWithdraw = availableBanknoteCount;
            } else {
                requestedCash -= requestedBanknotesCount * banknoteType.getBanknoteValue();
                banknotesCountToWithdraw = requestedBanknotesCount;
            }
            BanknoteCell banknoteCellToWithdraw = new BanknoteCell(banknoteType, banknotesCountToWithdraw);
            banknotesToWithdraw.add(banknoteCellToWithdraw);
        }
        return banknotesToWithdraw;
    }

    @Override
    public int getAvailableCashAmount() {
        int balance = 0;
        for (BanknoteType banknoteType: BanknoteType.values())
            balance += getBanknoteCount(banknoteType) * banknoteType.getBanknoteValue();
        return balance;
    }

    private List<BanknoteCell> getAvailableBanknoteCells(){
        List<BanknoteCell> banknoteCellList = banknoteCellRepository.getAll();

        return banknoteCellList.stream()
                .filter(banknoteCell -> !banknoteCell.isEmpty())
                .sorted((o1, o2) -> Integer.compare(o2.getBanknoteType().getBanknoteValue(),
                        o1.getBanknoteType().getBanknoteValue()))
                .collect(Collectors.toList());
    }

}
