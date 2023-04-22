package ru.otus.entity;

import java.util.Objects;

public record MenuItem(Integer id, String label) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem menuItem)) return false;
        return Objects.equals(id(), menuItem.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id());
    }
}
