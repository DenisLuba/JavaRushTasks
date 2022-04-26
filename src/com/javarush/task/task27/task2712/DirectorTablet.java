package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.Iterator;
import java.util.Locale;
import java.util.SortedMap;

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

        System.out.printf(Locale.US, "Total - %.2f", total);
    }

    public void printCookWorkloading(){ // напечатать загрузку (рабочее время) повара; сгруппировать по дням

    }

    public void printActiveVideoSet(){ // напечатать список активных видеороликов и оставшееся количество показов по каждому

    }
    public void printArchivedVideoSet(){ // напечатать список неактивных роликов (с нулем оставшихся показов)

    }
}
