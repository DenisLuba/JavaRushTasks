package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;

import java.util.*;

public class DirectorTablet {
    StatisticManager statisticManager = StatisticManager.getInstance();
    public void printAdvertisementProfit(){ // напечатать сумму, которую заработали на рекламе; сгруппировать по дням
        SortedMap<String, Long> map = statisticManager.getMapOfAmountsByDate();
        Iterator<String> iterator = map.keySet().iterator();
        double total = .0;
        while(iterator.hasNext()) {
            String key = (String) iterator.next();
            double value = map.get(key);
            double amount = value / 100;
            System.out.printf(Locale.US, "%s - %.2f\n", key, amount);

            total += amount;
        }

        System.out.printf(Locale.US, "Total - %.2f\n", total);
    }

    public void printCookWorkloading(){ // напечатать загрузку (рабочее время) повара; сгруппировать по дням
        SortedMap<String, ArrayList<EventDataRow>> map = statisticManager.getMapByDate(EventType.COOKED_ORDER);
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            ArrayList<EventDataRow> eventDataRows = map.get(key);
            Collections.sort(eventDataRows, new Comparator<EventDataRow>() {
                @Override
                public int compare(EventDataRow o1, EventDataRow o2) {
                    CookedOrderEventDataRow event1 = (CookedOrderEventDataRow) o1;
                    CookedOrderEventDataRow event2 = (CookedOrderEventDataRow) o2;
                    return event1.getCookName().compareTo(event2.getCookName());
                }
            });

            System.out.println(key);
            for(EventDataRow eventDataRow : eventDataRows) {
                CookedOrderEventDataRow cookedOrderEventDataRow = (CookedOrderEventDataRow) eventDataRow;
                String name = cookedOrderEventDataRow.getCookName();
                double durationInSeconds = cookedOrderEventDataRow.getTime();
                int durationInMinutes = (int) Math.ceil(durationInSeconds / 60);
                System.out.printf("%s - %d min\n", name, durationInMinutes);
            }
            System.out.println();
        }
    }

    public void printActiveVideoSet(){ // напечатать список активных видеороликов и оставшееся количество показов по каждому

    }
    public void printArchivedVideoSet(){ // напечатать список неактивных роликов (с нулем оставшихся показов)

    }
}
