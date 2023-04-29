package ru.otus.processor.impl;

import ru.otus.entity.Cash;
import ru.otus.exception.IncorrectRequestedAmount;
import ru.otus.exception.NotEnoughCashException;
import ru.otus.formatter.CashWithdrawMessageFormatter;
import ru.otus.processor.AtmProcessor;
import ru.otus.service.*;

public class CashWithdrawProcessor implements AtmProcessor {

    private static final String REQUEST_CASH_AMOUNT = "Please, enter the amount of cash to withdraw";

    private final OutputService outputService;

    private final InputService inputService;

    private final BanknoteCellService banknoteCellService;

    private final DispenserService dispenserService;

    private final CashWithdrawMessageFormatter cashWithdrawMessageFormatter = new CashWithdrawMessageFormatter();

    public CashWithdrawProcessor(OutputService outputService,
                                 InputService inputService,
                                 BanknoteCellService banknoteCellService,
                                 DispenserService dispenserService){
        this.outputService = outputService;
        this.inputService = inputService;
        this.banknoteCellService = banknoteCellService;
        this.dispenserService = dispenserService;
    }

    @Override
    public void process() {
        try {
            int requestedCashAmount = getRequestedCashAmount();
            int availableCashAmount = banknoteCellService.getAvailableCashAmount();
            if (availableCashAmount < requestedCashAmount){
                throw new NotEnoughCashException();
            }
            Cash cash = tryToWithdrawCash(requestedCashAmount);
            dispenserService.withdraw(cash);
            String formattedCashReport = cashWithdrawMessageFormatter.format(cash);
                outputService.print("Success! \n" + formattedCashReport);
        } catch (IncorrectRequestedAmount e){
            outputService.print("Incorrect input, operation finished");
        } catch (NotEnoughCashException e){
            outputService.print("Not enough cash, operation finished");
        }
    }

    private int getRequestedCashAmount(){
        int requestedAmount;
        try {
            outputService.print(REQUEST_CASH_AMOUNT);
            String userMessage = inputService.read();
            requestedAmount = Integer.parseInt(userMessage);
            if (requestedAmount < 1){
                throw new IncorrectRequestedAmount("Requested amount cannot be below 1");
            }
        } catch (NumberFormatException e){
            throw new IncorrectRequestedAmount(e);
        }
        return requestedAmount;
    }

    private Cash tryToWithdrawCash(int requestedCashAmount){
        Cash possibleCashWithdrawal = dispenserService.getPossibleCashWithdrawal(requestedCashAmount);
        var possibleCashWithdrawalAmount = possibleCashWithdrawal.getTotal();
        if (possibleCashWithdrawalAmount < requestedCashAmount ){
            String question = String.format("Not enough banknotes for hole requested cash. " +
                            "It is possible to withdraw %d. Withdraw less", possibleCashWithdrawalAmount);
            outputService.print(question);
        }
        return possibleCashWithdrawal;
    }
}
