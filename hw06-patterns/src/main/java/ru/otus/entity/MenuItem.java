package ru.otus.entity;

import java.util.Objects;

public record MenuItem(Integer id, String label) implements Comparable<MenuItem>{

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

    @Override
    public int compareTo(MenuItem o) {
        if (this.id < o.id) {
            return -1;
        } else if (this.id > o.id){
            return 1;
        }
        return 0;
    }
}
