package ru.otus.service;

import ru.otus.entity.Cash;

public interface CashService {

    Cash getPossibleCashWithdrawal(int requestedAmount);

    void withdraw(Cash requestedCash);

    int getAvailableCashAmount();
}
