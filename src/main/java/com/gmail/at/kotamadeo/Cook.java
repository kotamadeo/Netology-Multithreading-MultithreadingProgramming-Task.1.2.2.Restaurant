package com.gmail.at.kotamadeo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Cook implements Runnable {
    private boolean continueWorking = true;
    private Lock lock = new ReentrantLock();
    private static final int COOK_SLEEP_TIME = 100;

    @Override
    public void run() {
        boolean needToWait;
        while (continueWorking || needToCookOrders()) {
            lock.lock();
            try {
                needToWait = !needToCookOrders();
                if (!needToWait) {
                    cook();
                }
                if (continueWorking && needToWait) {
                    System.out.println("Повар отдыхает");
                    MILLISECONDS.sleep(COOK_SLEEP_TIME);
                }
            } catch (InterruptedException ignored) {
            } finally {
                lock.unlock();
            }
        }
    }

    private boolean needToCookOrders() {
        return !Manager.getInstance().getOrderQueue().isEmpty();
    }

    private void cook() throws InterruptedException {
        Manager manager = Manager.getInstance();
        Order order = manager.getOrderQueue().poll();
        System.out.println(String.format("Заказ будет готовиться %d мс для стола №%d", order.getTime(), order.getTableNumber()));
        sleep(order.getTime());
        Dishes dishes = new Dishes(order.getTableNumber());
        System.out.println(String.format("Заказ для стола №%d готов", dishes.getTableNumber()));
        manager.getDishesQueue().add(dishes);
    }

    public void setContinueWorking(boolean continueWorking) {
        this.continueWorking = continueWorking;
    }
}
