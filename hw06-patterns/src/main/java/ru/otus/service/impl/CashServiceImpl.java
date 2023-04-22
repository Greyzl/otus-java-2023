package ru.otus.service.impl;

import ru.otus.entity.BanknotePack;
import ru.otus.entity.enums.BanknoteType;
import ru.otus.entity.Cash;
import ru.otus.service.BanknoteCellService;
import ru.otus.service.CashService;

public class CashServiceImpl implements CashService {

    private final BanknoteCellService banknoteCellService;

    public CashServiceImpl(BanknoteCellService banknoteCellService){
        this.banknoteCellService = banknoteCellService;
    }

    @Override
    public Cash getPossibleCashWithdrawal(int requestedAmount) {
        Cash cash = new Cash();
        var availableBanknotePacks = banknoteCellService.getAvailableBanknotePacks();
        var sortedByTypeAvailableBanknotePacks = availableBanknotePacks.stream()
                .sorted((banknotePack, banknotePack1) -> Integer.compare(
                        banknotePack1.getBanknoteType().getBanknoteValue(),
                        banknotePack.getBanknoteType().getBanknoteValue()))
                .toList();
        for (BanknotePack availableBanknotePack : sortedByTypeAvailableBanknotePacks){
            BanknoteType currentBanknoteType = availableBanknotePack.getBanknoteType();
            int requestedBanknoteCount = requestedAmount / availableBanknotePack.getBanknoteType().getBanknoteValue();
            int availableBanknoteCount = availableBanknotePack.getBanknoteCount();
            if (requestedBanknoteCount >= availableBanknoteCount){
                requestedAmount -= availableBanknoteCount * currentBanknoteType.getBanknoteValue();
                cash.addBanknoteCount(availableBanknotePack.getBanknoteType(), availableBanknoteCount);
            } else {
                requestedAmount -= requestedBanknoteCount * currentBanknoteType.getBanknoteValue();
                cash.addBanknoteCount(currentBanknoteType, requestedBanknoteCount);
            }
        }
        return cash;
    }

    public void withdraw(Cash requestedCash){
        var requestedCashBanknotePacks = requestedCash.getBanknotePacks();
        requestedCashBanknotePacks.forEach(
                requestedCashBanknotePack -> banknoteCellService.withdrawBanknoteCount(
                        requestedCashBanknotePack.getBanknoteType(),
                        requestedCashBanknotePack.getBanknoteCount()
                )
        );
    }

    @Override
    public int getAvailableCashAmount() {
        return banknoteCellService.getAvailableCashAmount();
    }
}
