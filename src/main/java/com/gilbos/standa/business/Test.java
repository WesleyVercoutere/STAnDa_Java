package com.gilbos.standa.business;

import java.util.Objects;

public class Test {

    private String id;
    private int total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return id.equals(test.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
