package com.javarush.task.task27.task2712.statistic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

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

    public SortedMap<String, Long> getMapOfAmountsByDate() {
        HashMap<String, Long> mapOfAmountsByDate = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH);

        // пробежимся по хранилищу класса StaticStorage, создадим новую мапу с соответствием даты и суммы каждого видео-события
        for (Map.Entry<EventType, List<EventDataRow>> entry : statisticStorage.getStorage().entrySet()) {
            if (entry.getKey() == EventType.SELECTED_VIDEOS) { // выберем только события, связанные с рекламой
                ArrayList<EventDataRow> eventDataRows = (ArrayList<EventDataRow>) entry.getValue();
                for (EventDataRow eventDataRow : eventDataRows) { // пробежимся по всем событиям в списке видео-событий
                    VideoSelectedEventDataRow videoSelectedEventDataRow = (VideoSelectedEventDataRow) eventDataRow;
                    // добавим в результирующую мапу соответствие дат нужного формата типа String и сумм в копейках, полученных за рекламу
                    String date = formatter.format(videoSelectedEventDataRow.getDate());
                    long amount = videoSelectedEventDataRow.getAmount();
                    if (mapOfAmountsByDate.containsKey(date)) amount += mapOfAmountsByDate.get(date);
                    mapOfAmountsByDate.put(date, amount);
                }
            }
        }

        // поместим мапу в тримапу для сортировки по дате через формат даты
        SortedMap<String, Long> sortedMap = new TreeMap<>(Collections.reverseOrder(new Comparator<String>() {
            DateFormat format = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH);
            @Override
            public int compare(String o1, String o2) {
               try {
                   return format.parse(o1).compareTo(format.parse(o2));
               } catch (ParseException e) {
                   throw new IllegalArgumentException(e);
               }
            }
        }));

        sortedMap.putAll(mapOfAmountsByDate);

        return sortedMap;
    }


    private class StatisticStorage {
        Map<EventType, List<EventDataRow>> storage;

        public StatisticStorage() {
            storage = new HashMap<>();
            for (EventType eventType : EventType.values())
                storage.put(eventType, new ArrayList<EventDataRow>());
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }
    }
}
