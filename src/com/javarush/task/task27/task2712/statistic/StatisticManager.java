package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.statistic.event.EventDataRow;

public class StatisticManager {
    private static StatisticManager INSTANCE;

    private StatisticManager() {}

    public static StatisticManager getInstance() {
        if (INSTANCE == null) INSTANCE = new StatisticManager();
        return INSTANCE;
    }

    public void register(EventDataRow data) {

    }
}
