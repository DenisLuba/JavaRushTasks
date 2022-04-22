package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.Objects;

public class Advertisement {
    private Object content; // видео рекламы
    private String name; // название рекламы
    private long initialAmount; // сумма за все показы ролика в копейках
    private int hits; // количество оплаченных показов рекламы
    private int duration; // продолжительность рекламы в секундах
    private long amountPerOneDisplaying; // стоимость одного ролика в копейках

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        try {
            amountPerOneDisplaying = initialAmount / hits;
        } catch (ArithmeticException e) {
            ConsoleHelper.writeMessage("Количество показов равно нулю.");
        }
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public void revalidate() throws UnsupportedOperationException { // уменьшение количества оставшихся показов этого ролика
        if (hits <= 0) throw new UnsupportedOperationException();
        hits--;
    }

    public boolean isActive() {return hits > 0;}
}
