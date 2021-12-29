package com.example.rockerfockers;

import java.io.Serializable;

public class Resource implements Serializable {
    private static int instance = 0;
    private int id;
    private final String rName;
    private int count;

    public Resource() {
        id = instance++;
        rName = "Resource " + id;
        count = 0;
    }

    public Resource(String name, int count) {
        id = instance++;
        rName = name;
        this.count = count;
    }

    public int getInstance() {
        return instance;
    }

    public String getName() {
        return rName;
    }

    public int getCount() {
        return count;
    }

    public void add() {
        this.add(1);
    }

    public void add(int num) {
        count += num;
    }

    public void subtract() {
        this.subtract(1);
    }

    public void subtract(int num) {
        count -= Math.min(count, Math.max(num, 0));
    }

    public boolean nonzero() {
        return count != 0;
    }
}
