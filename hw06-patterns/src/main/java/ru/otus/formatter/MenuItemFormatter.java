package ru.otus.formatter;

import ru.otus.entity.MenuItem;

public class MenuItemFormatter {
    public String format(MenuItem menuItem){
        return String.format("%d: %s",menuItem.getId(), menuItem.getLabel());
    }
}
