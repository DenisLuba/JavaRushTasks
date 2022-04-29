package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;

    public int getOrderCreatingInterval() {
        return ORDER_CREATING_INTERVAL;
    }
    public static void main(String[] args) throws IOException {
        List<Tablet> tabletList = new ArrayList<Tablet>();
        tabletList.add(new Tablet(1));
        tabletList.add(new Tablet(2));
        tabletList.add(new Tablet(3));
        tabletList.add(new Tablet(4));

//        Tablet tablet = new Tablet(5);
        Cook cook = new Cook("Amigo");
        Waiter waiter = new Waiter();
        DirectorTablet directorTablet = new DirectorTablet();

        for(Tablet tablet : tabletList)
            tablet.addObserver(cook);
//        tablet.addObserver(cook);
        cook.addObserver(waiter);

        Thread thread = new Thread(new RandomOrderGeneratorTask(tabletList, ORDER_CREATING_INTERVAL));
        thread.start();
//        tablet.createOrder();
//        tablet.createOrder();
//        tablet.createOrder();
//        tablet.createOrder();

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {

        }

        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }


}
