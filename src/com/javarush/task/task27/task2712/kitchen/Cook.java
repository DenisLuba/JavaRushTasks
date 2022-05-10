package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable {

    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();
    private String name;
    private boolean busy;

    public Cook(String name) {
        this.name = name;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public boolean isBusy() {
        return busy;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) {
        busy = true;
        ConsoleHelper.writeMessage("Start cooking - " + order + ", cooking time " + order.getTotalCookingTime() + "min");
        setChanged();
        notifyObservers(order);
        StatisticManager statisticManager = StatisticManager.getInstance();
        statisticManager.register(new CookedOrderEventDataRow(order.getTablet().toString(), name, order.getTotalCookingTime(), order.getDishes()));
        try {
            Thread.sleep(order.getTotalCookingTime() * 10L);
        } catch (InterruptedException e) {
            ConsoleHelper.writeMessage("InterruptedException in method startCookingOrder");
        } finally {
            busy = false;
        }
    }

    @Override
    public void run() {
        Order order;
        while (true) {
            if(!this.isBusy() && ((order = queue.poll()) != null))
                this.startCookingOrder(order);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//        Set<Cook> cooks = StatisticManager.getInstance().getCooks(); // отсюда будем брать список поваров
//        Order order;
//
//        while (true) { // вечный фоновый цикл поиска заказов и передачи их свободным поварам
//            for(Cook cook : cooks) {
//                if(!cook.isBusy() && ((order = queue.poll()) != null))
//                    cook.startCookingOrder(order);
//            }
//
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
}
