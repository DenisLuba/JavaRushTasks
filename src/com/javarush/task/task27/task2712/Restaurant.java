package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    public int getOrderCreatingInterval() {
        return ORDER_CREATING_INTERVAL;
    }
    public static void main(String[] args) throws IOException {
        Cook cookAmigo = new Cook("Amigo");
        cookAmigo.setQueue(orderQueue);
        Cook cookRobert = new Cook("Robert");
        cookRobert.setQueue(orderQueue);

        StatisticManager statisticManager = StatisticManager.getInstance();

        Waiter waiter = new Waiter();
        DirectorTablet directorTablet = new DirectorTablet();
        List<Tablet> tablets = new ArrayList<Tablet>();
        for(int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        cookAmigo.addObserver(waiter);
        cookRobert.addObserver(waiter);

        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        Thread thread1 = new Thread(cookAmigo);
        Thread thread2 = new Thread(cookRobert);
        thread.start();
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(1000);
            thread.interrupt();
        } catch (InterruptedException e) {

        }

        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
