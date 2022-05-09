package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderManager implements Observer {
    private LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    private StatisticManager statisticManager = StatisticManager.getInstance();

    public OrderManager() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Set<Cook> cooks = statisticManager.getCooks(); // отсюда будем брать список поваров
                Order order;

                while (true) { // вечный фоновый цикл поиска заказов и передачи их свободным поварам
                    for(Cook cook : cooks) {
                        if(!cook.isBusy() && ((order = orderQueue.poll()) != null))
                            cook.startCookingOrder(order);
                    }

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            orderQueue.put((Order) arg);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
