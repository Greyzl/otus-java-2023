package ru.otus.repository.impl;

import ru.otus.entity.MenuItem;
import ru.otus.processor.MenuProcessor;
import ru.otus.repository.MenuProcessorRepository;

import java.util.HashMap;
import java.util.Map;

public class MenuProcessorRepositoryImpl implements MenuProcessorRepository {

    private final Map<MenuItem, MenuProcessor> menuProcessorMap = new HashMap<>();

    public void add(MenuItem menuItem, MenuProcessor menuProcessor){
        menuProcessorMap.put(menuItem, menuProcessor);
    }
    @Override
    public MenuProcessor getByMenuItem(MenuItem menuItem) {
        return menuProcessorMap.get(menuItem);
    }
}
