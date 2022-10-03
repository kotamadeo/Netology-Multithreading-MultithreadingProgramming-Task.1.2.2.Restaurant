package com.gmail.at.kotamadeo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    private static final int COOK_SLEEP_TIME = 100;
    private static final int WAITER_SLEEP_TIME = 5;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Waiter waiterTarget = new Waiter();
        executorService.execute(waiterTarget);
        Cook cookTarget = new Cook();
        executorService.execute(cookTarget);
        SECONDS.sleep(COOK_SLEEP_TIME);
        cookTarget.setContinueWorking(false);
        SECONDS.sleep(WAITER_SLEEP_TIME);
        waiterTarget.setContinueWorking(true);
        executorService.shutdown();
    }
}