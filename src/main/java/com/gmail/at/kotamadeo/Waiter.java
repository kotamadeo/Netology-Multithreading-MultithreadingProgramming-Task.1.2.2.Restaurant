package com.gmail.at.kotamadeo;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Waiter implements Runnable {
    private boolean continueWorking = true;
    private static final int WAITER_SLEEP_TIME = 100;

    @Override
    public void run() {
        Manager manager = Manager.getInstance();
        while (continueWorking || !manager.getDishesQueue().isEmpty()) {
            if (!manager.getDishesQueue().isEmpty()) {
                Dishes dishes = manager.getDishesQueue().poll();
                System.out.println("Официант отнес заказ для стола №" + dishes.getTableNumber());
            } else {
                Table table = manager.getNextTable();
                Order order = table.getOrder();
                System.out.println("Получен заказ от стола №" + order.getTableNumber());
                manager.getOrderQueue().add(order);
            }
            try {
                MILLISECONDS.sleep(WAITER_SLEEP_TIME);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void setContinueWorking(boolean continueWorking) {
        this.continueWorking = continueWorking;
    }
}
