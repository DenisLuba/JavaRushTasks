package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;

    public int getOrderCreatingInterval() {
        return ORDER_CREATING_INTERVAL;
    }
    public static void main(String[] args) throws IOException {
        Cook cookAmigo = new Cook("Amigo");
        Cook cookRobert = new Cook("Robert");

        StatisticManager statisticManager = StatisticManager.getInstance();
        statisticManager.register(cookAmigo);
        statisticManager.register(cookRobert);

        Waiter waiter = new Waiter();
        DirectorTablet directorTablet = new DirectorTablet();
        OrderManager orderManager = new OrderManager();
        List<Tablet> tablets = new ArrayList<Tablet>();
        for(int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablets.add(tablet);
            tablet.addObserver(orderManager);
            tablet.addObserver(orderManager);
        }

        cookAmigo.addObserver(waiter);
        cookRobert.addObserver(waiter);

        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        thread.start();

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
