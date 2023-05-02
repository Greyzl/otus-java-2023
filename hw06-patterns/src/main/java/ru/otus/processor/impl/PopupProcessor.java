package ru.otus.processor.impl;

import ru.otus.entity.BanknotePack;
import ru.otus.entity.Cash;
import ru.otus.entity.enums.BanknoteType;
import ru.otus.exception.IncorrectBanknoteCountException;
import ru.otus.processor.AtmProcessor;
import ru.otus.service.BanknoteCellService;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;

import java.util.List;

public class PopupProcessor implements AtmProcessor {

    private final InputService inputService;

    private final OutputService outputService;

    private final BanknoteCellService banknoteCellService;

    public PopupProcessor(InputService inputService,
                          OutputService outputService,
                          BanknoteCellService banknoteCellService){
        this.inputService = inputService;
        this.outputService = outputService;
        this.banknoteCellService = banknoteCellService;
    }
    @Override
    public void process() {
        try {
            Cash inputCash = getUserCash();
            List<BanknotePack> banknotePacks = inputCash.getBanknotePacks();
            for (BanknotePack banknotePack: banknotePacks){
                banknoteCellService.addBanknoteCount(banknotePack.getBanknoteType(),
                        banknotePack.getBanknoteCount());
            }
            outputService.print("Success");
        } catch (IncorrectBanknoteCountException e){
            outputService.print("Incorrect banknote count. Operation finitshed");
        }
    }

    private Cash getUserCash(){
        Cash inputCash = new Cash();
        try {
            for (BanknoteType banknoteType: BanknoteType.values()){
                outputService.print(getBanknoteInputMessage(banknoteType));
                String userInput = inputService.read();
                int banknoteTypeAmount = Integer.parseInt(userInput);
                inputCash.addBanknoteCount(banknoteType, banknoteTypeAmount);
            }
        } catch (NumberFormatException e){
            throw new IncorrectBanknoteCountException();
        }
        return inputCash;
    }

    private String getBanknoteInputMessage(BanknoteType banknoteType){
        return "How many " + banknoteType.getBanknoteValue() + " you want to add? Please enter amount";
    }
}
