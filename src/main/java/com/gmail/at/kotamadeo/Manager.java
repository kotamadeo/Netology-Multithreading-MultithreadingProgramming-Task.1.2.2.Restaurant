package com.gmail.at.kotamadeo;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Manager {
    private static Manager manager;
    private static Lock lock = new ReentrantLock();
    private static final int TABLE_AMOUNT = 10;

    private final List<Table> restaurantTables = new ArrayList<>();
    private int currentIndex;

    private final Queue<Order> orderQueue = new ConcurrentLinkedQueue<>();
    private final Queue<Dishes> dishesQueue = new ConcurrentLinkedQueue<>();

    public static Manager getInstance() {
        lock.lock();
        try {
            if (manager == null) {
                manager = new Manager();
            }
            return manager;
        } finally {
            lock.unlock();
        }
    }

    private Manager() {
        for (int i = 0; i < TABLE_AMOUNT; i++) {
            restaurantTables.add(new Table());
        }
    }



    public Table getNextTable() {
        lock.lock();
        try {
            Table table = restaurantTables.get(currentIndex);
            currentIndex = (currentIndex + 1) % 10;
            return table;
        } finally {
            lock.unlock();
        }
    }

    public Queue<Order> getOrderQueue() {
        return orderQueue;
    }

    public Queue<Dishes> getDishesQueue() {
        return dishesQueue;
    }
}
