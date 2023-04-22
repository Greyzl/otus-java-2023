package ru.otus.processor.impl;

import ru.otus.entity.Cash;
import ru.otus.entity.enums.Permission;
import ru.otus.exception.IncorrectMenuItemException;
import ru.otus.exception.IncorrectRequestedAmount;
import ru.otus.exception.NotEnoughCashException;
import ru.otus.formatter.CashWithdrawMessageFormatter;
import ru.otus.processor.ATMProcessor;
import ru.otus.repository.PermissionMenuItemRepository;
import ru.otus.service.CashService;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;
import ru.otus.service.PermissionService;

import java.util.Optional;

public class CashWithdrawProcessor implements ATMProcessor {

    private static final String REQUEST_CASH_AMOUNT = "Please, enter the amount of cash to withdraw";

    private final OutputService outputService;

    private final InputService inputService;

    private final CashService cashService;

    private final CashWithdrawMessageFormatter cashWithdrawMessageFormatter = new CashWithdrawMessageFormatter();

    private final PermissionService permissionService;

    public CashWithdrawProcessor(OutputService outputService,
                                 InputService inputService,
                                 CashService cashService,
                                 PermissionService permissionService){
        this.outputService = outputService;
        this.inputService = inputService;
        this.cashService = cashService;
        this.permissionService = permissionService;
    }

    @Override
    public void process() {
        try {
            int requestedCashAmount = getRequestedCashAmount();
            int availableCashAmount = cashService.getAvailableCashAmount();
            if (availableCashAmount < requestedCashAmount){
                throw new NotEnoughCashException();
            }
            Optional<Cash> mayByCash = tryToWithdrawCash(requestedCashAmount);
            if (mayByCash.isPresent()){
                cashService.withdraw(mayByCash.get());
                String formattedCashReport = cashWithdrawMessageFormatter.format(mayByCash.get());
                outputService.print("Success! \n" + formattedCashReport);
            } else {
                outputService.print("Operation finished");
            }
        } catch (IncorrectRequestedAmount e){
            outputService.print("Incorrect input, operation finished");
        } catch (NotEnoughCashException e){
            outputService.print("Not enough cash, operation finished");
        } catch (IncorrectMenuItemException e){

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

    private Optional<Cash> tryToWithdrawCash(int requestedCashAmount){
        Cash possibleCashWithdrawal = cashService.getPossibleCashWithdrawal(requestedCashAmount);
        var possibleCashWithdrawalAmount = possibleCashWithdrawal.getTotal();
        if (possibleCashWithdrawalAmount < requestedCashAmount ){
            String question = String.format("Not enough banknotes for hole requested cash. " +
                            "It is possible to withdraw %d. Proceed?", possibleCashWithdrawalAmount);
            Permission permission = permissionService.askForPermission(question);
            if (permission == Permission.DENIED){
                possibleCashWithdrawal = null;
            }
        }
        return Optional.ofNullable(possibleCashWithdrawal);
    }
}
