package ru.otus.service;

import ru.otus.entity.Cash;

public interface DispenserService {

    Cash getPossibleCashWithdrawal(int requestedAmount);

    void withdraw(Cash requestedCash);
}
