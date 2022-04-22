package com.javarush.task.task27.task2712.kitchen;

public enum Dish {
    FISH(25),
    STEAK(30),
    SOUP(15),
    JUICE(5),
    WATER(3);

    private int duration;

    public int getDuration() {
        return duration;
    }

    Dish(int duration) {
        this.duration = duration;
    }

    public static String allDishesToString() {
        StringBuilder string = new StringBuilder();
        for (Dish dish : Dish.values()) {
            string.append(dish).append(", ");
        }
        return string.substring(0, string.lastIndexOf(","));
    }

    public static void main(String[] args) {
        System.out.println(allDishesToString());
    }
}
