package com.javarush.task.task27.task2712.statistic.event;

import java.util.Date;
import java.util.List;

import com.javarush.task.task27.task2712.kitchen.Dish;

public class CookedOrderEventDataRow implements EventDataRow {
    private String tabletName; // имя планшета
    private String cookName; // имя повара
    private int cookingTimeSeconds; // время приготовления заказа в секундах
    private List<Dish> cookingDishes; // список блюд для приготовления
    private Date currentDate;

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishes) {
        this.cookName = cookName;
        this.tabletName = tabletName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishes = cookingDishes;
        currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return cookingTimeSeconds;
    }
}
