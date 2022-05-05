package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;

public class Cook extends Observable {
    private String name;
    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) {
        ConsoleHelper.writeMessage("Start cooking - " + order + ", cooking time " + order.getTotalCookingTime() + "min");
        setChanged();
        notifyObservers(order);
        StatisticManager statisticManager = StatisticManager.getInstance();
        statisticManager.register(new CookedOrderEventDataRow(order.getTablet().toString(), name, order.getTotalCookingTime(), order.getDishes()));
    }
}
