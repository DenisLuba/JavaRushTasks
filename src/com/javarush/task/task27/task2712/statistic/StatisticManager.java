package com.javarush.task.task27.task2712.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;

public class StatisticManager {
    private static StatisticManager INSTANCE;
    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {}

    public static StatisticManager getInstance() {
        if (INSTANCE == null) INSTANCE = new StatisticManager();
        return INSTANCE;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
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
