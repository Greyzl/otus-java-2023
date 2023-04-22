package ru.otus.formatter;

import ru.otus.entity.BanknotePack;
import ru.otus.entity.Cash;

public class CashWithdrawMessageFormatter {
    public String format(Cash cash){
        StringBuilder builder = new StringBuilder();
        for (BanknotePack banknotePack: cash.getBanknotePacks()){
            builder.append("Banknote ").append(banknotePack.getBanknoteType())
                    .append(", count: ").append(banknotePack.getBanknoteCount())
                    .append("\n");
        }
        builder.append("Total amount: ").append(cash.getTotal());
        return builder.toString();
    }
}