package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager statisticAdvertisementManager = new StatisticAdvertisementManager();
    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {
    }

    public static StatisticAdvertisementManager getInstance() {
        return statisticAdvertisementManager;
    }

    public ArrayList<Advertisement> getListOfAds(boolean active){
        ArrayList<Advertisement> listOfAllVideos = (ArrayList<Advertisement>) storage.list();
        ArrayList<Advertisement> result = new ArrayList<>();

        for (Advertisement advertisement : listOfAllVideos) {
            if (active ^ !advertisement.isActive()) result.add(advertisement);
        }

        return result;
    }
}
