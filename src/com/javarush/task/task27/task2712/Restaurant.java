package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private final static LinkedBlockingQueue<Order> ORDER_QUEUE = new LinkedBlockingQueue<>();
    public int getOrderCreatingInterval() {
        return ORDER_CREATING_INTERVAL;
    }
    public static void main(String[] args) throws IOException {
        Cook cookAmigo = new Cook("Amigo");
        cookAmigo.setQueue(ORDER_QUEUE);
        Cook cookRobert = new Cook("Robert");
        cookRobert.setQueue(ORDER_QUEUE);

        //        StatisticManager statisticManager = StatisticManager.getInstance();

        Waiter waiter = new Waiter();
        DirectorTablet directorTablet = new DirectorTablet();
        List<Tablet> tablets = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(ORDER_QUEUE);
            tablets.add(tablet);
        }

        cookAmigo.addObserver(waiter);
        cookRobert.addObserver(waiter);

        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        Thread thread1 = new Thread(cookAmigo);
        Thread thread2 = new Thread(cookRobert);

        thread.setDaemon(true);
        thread1.setDaemon(true);
        thread2.setDaemon(true);

        thread.start();
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
