package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {

    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();
    private final int number;
    public static Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet(int number) {
        this.number = number;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Order createOrder() {
        Order order = null;
        try {
            order = new Order(this);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
        assert order != null;
        orderSetup(order);

        return order;
    }

    public void createTestOrder() {
        Order order = null;
        try {
            order = new TestOrder(this);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
        assert order != null;
        orderSetup(order);
    }

    private void orderSetup(Order order) {
        assert order != null;
        ConsoleHelper.writeMessage(order.toString());

        AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
        StatisticManager statisticManager = StatisticManager.getInstance();

        try {
            advertisementManager.processVideos();
        } catch(NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order" + order);
        }
        statisticManager.register(new VideoSelectedEventDataRow(advertisementManager.getOptimalVideoSet(), advertisementManager.getMaxAmount(), advertisementManager.getTotalTimeSecondsLeft()));
        if (!order.isEmpty()) {
            queue.add(order);
        }
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}
