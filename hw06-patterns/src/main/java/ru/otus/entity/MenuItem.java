package ru.otus.entity;

import ru.otus.processor.ATMProcessor;

import java.util.List;

public class MenuItem {

    private Integer id;

    private String label;

    private ATMProcessor atmProcessor;

    public MenuItem(Integer id, String label, ATMProcessor atmProcessor){
        this.id = id;
        this.label = label;
        this.atmProcessor = atmProcessor;
    }


    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public ATMProcessor getAtmProcessor() {
        return atmProcessor;
    }

}
