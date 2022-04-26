package com.javarush.task.task27.task2712.statistic;

import java.util.*;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;

public class StatisticManager {
    private static StatisticManager INSTANCE;
    private StatisticStorage statisticStorage = new StatisticStorage();
    private Set cooks = new HashSet();

    private StatisticManager() {}

    public static StatisticManager getInstance() {
        if (INSTANCE == null) INSTANCE = new StatisticManager();
        return INSTANCE;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public void register(Cook cook) {
        cooks.add(cook);
    }

    private class StatisticStorage {
        Map<EventType, List<EventDataRow>> storage;

        public StatisticStorage() {
            storage = new HashMap<>();
            for (EventType eventType : EventType.values())
                storage.put(eventType, new ArrayList<EventDataRow>());
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }
    }
}
