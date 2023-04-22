package ru.otus.processor.impl;

import ru.otus.entity.BanknoteCell;
import ru.otus.entity.BanknoteType;
import ru.otus.exception.NotEnoughCashException;
import ru.otus.processor.ATMProcessor;
import ru.otus.service.BanknoteCellService;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;

import java.util.ArrayList;
import java.util.List;

public class CashWithdrawProcessor implements ATMProcessor {

    private final OutputService outputService;

    private final InputService inputService;

    private final BanknoteCellService banknoteCellService;

    public CashWithdrawProcessor(OutputService outputService,
                                 InputService inputService,
                                 BanknoteCellService banknoteCellService){
        this.outputService = outputService;
        this.inputService = inputService;
        this.banknoteCellService = banknoteCellService;
    }

    @Override
    public void process() {
        try {
            outputService.print(getWithdrawCashMessage());
            String userMessage = inputService.read();
            int requestedCashAmount = Integer.parseInt(userMessage);
            int availableCashAmount = banknoteCellService.getAvailableCashAmount();
            if (availableCashAmount < requestedCashAmount){
                throw new NotEnoughCashException();
            }
            outputService.print(withdraw(requestedCashAmount));
        } catch (NumberFormatException e){
            outputService.print("Incorrect input, operation finished");
        } catch (NotEnoughCashException e){
            outputService.print("Not enough cash, operation finished");
        }
    }

    private String getWithdrawCashMessage(){
        return "Please, enter the amount of cash to withdraw";
    }

    private String withdraw(int requestedCash){
        List<BanknoteCell> banknoteCellsToWithdraw = banknoteCellService.getPossibleWithdraw(requestedCash);
        int cashAmountToWithdraw = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("Success!\n");
        for (BanknoteCell banknoteCellToWithdraw: banknoteCellsToWithdraw){
            builder.append("Banknote ")
                    .append(banknoteCellToWithdraw.getBanknoteType())
                    .append(", count: ")
                    .append(banknoteCellToWithdraw.getBanknoteCount())
                    .append("\n");
        }
        return builder.toString();
    }

}
