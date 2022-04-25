package com.javarush.task.task27.task2712.statistic.event;

import java.util.Date;
import java.util.List;

import com.javarush.task.task27.task2712.ad.Advertisement;

public class VideoSelectedEventDataRow implements EventDataRow {
    private List<Advertisement> optimalVideoSet; // подобранная реклама
    private long amount; // общая сумма за просмотренную рекламу в копейках
    private int totalDuration; // общая продолжительность рекламы в секундах
    private Date currentDate; // текущая дата

    public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration) {
        this.optimalVideoSet = optimalVideoSet;
        this.amount = amount;
        this.totalDuration = totalDuration;
        currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.SELECTED_VIDEOS;
    }
}
