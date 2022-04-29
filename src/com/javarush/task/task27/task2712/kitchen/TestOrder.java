package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() {
//        System.out.println("initDishes in TestOrder!!!");
        dishes = new ArrayList<>();
        int numberOfDishes = (int) (Math.random() * 10) + 1; // рандомное количество блюд в заказе от 1 до 10
        Dish[] arrayDishes = Dish.values(); // получим копию списка блюд
        for (int i = 0; i < numberOfDishes; i++) {
            int dishNumber = (int) (Math.random() * arrayDishes.length); // индекс рандомного блюда
            Dish dish = arrayDishes[dishNumber]; // получаем рандомное блюдо
            dishes.add(dish); // добавляем в рандомный список рандомное блюдо
        }
    }
}
