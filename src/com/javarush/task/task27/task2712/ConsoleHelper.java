package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));;

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return bufferedReader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        String replay;
        List<Dish> listDishes = new ArrayList<>();
        List<String> menu = new ArrayList<>();
        for (Dish dish : Dish.values()) menu.add(dish.toString());

        writeMessage("Введите название блюда или \"exit\" для выхода:\n" + Dish.allDishesToString());

        while (true){
            replay = readString().trim();
            if (replay.equals("exit")) break;

            if (menu.contains(replay)) {
                listDishes.add(Dish.valueOf(replay));
                writeMessage("Добавлено в список блюд: " + replay);
            } else writeMessage("Такого блюда нет в меню :(");
        }
        return listDishes;
    }
}
