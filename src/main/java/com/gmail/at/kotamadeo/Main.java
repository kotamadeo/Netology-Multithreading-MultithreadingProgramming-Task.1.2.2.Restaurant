package com.gmail.at.kotamadeo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    private static final int COOK_SLEEP_TIME = 2;
    private static final int WAITER_SLEEP_TIME = 500;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Waiter waiter = new Waiter();
        executorService.execute(waiter);
        Cook cook = new Cook();
        executorService.execute(cook);
        SECONDS.sleep(COOK_SLEEP_TIME);
        cook.setContinueWorking(false);
        MILLISECONDS.sleep(WAITER_SLEEP_TIME);
        waiter.setContinueWorking(false);
        executorService.shutdown();
    }
}