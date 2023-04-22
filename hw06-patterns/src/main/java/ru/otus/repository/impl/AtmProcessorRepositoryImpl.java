package ru.otus.repository.impl;

import ru.otus.entity.MenuItem;
import ru.otus.processor.ATMProcessor;
import ru.otus.repository.AtmProcessorRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AtmProcessorRepositoryImpl implements AtmProcessorRepository {
    private final Map<MenuItem, ATMProcessor> atmProcessorMap = new HashMap<>();

    @Override
    public void add(MenuItem menuItem, ATMProcessor atmProcessor) {
        atmProcessorMap.put(menuItem, atmProcessor);
    }

    @Override
    public Optional<ATMProcessor> get(MenuItem menuItem) {
        return Optional.ofNullable(atmProcessorMap.get(menuItem));
    }

    @Override
    public Optional<ATMProcessor> getByMenuId(int id) {
        MenuItem menuItem = new MenuItem(id, "stub");
        return Optional.ofNullable(atmProcessorMap.get(menuItem));
    }

    @Override
    public Set<MenuItem> getMenuItems() {
        return atmProcessorMap.keySet();
    }
}
