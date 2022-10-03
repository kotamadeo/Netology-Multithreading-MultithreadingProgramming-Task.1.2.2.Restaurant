package com.gmail.at.kotamadeo;

import java.util.Random;

public class Order {
    private long time;
    private static final int RANDOMIZER = 200;
    private byte tableNumber;

    public Order(byte tableNumber) {
        time = new Random().nextInt(RANDOMIZER);
        this.tableNumber = tableNumber;
    }

    public long getTime() {
        return time;
    }

    public byte getTableNumber() {
        return tableNumber;
    }
}
