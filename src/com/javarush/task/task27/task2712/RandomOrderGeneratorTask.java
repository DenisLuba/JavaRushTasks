package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Order;

import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tablets;
    private int orderCreatingInterval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int orderCreatingInterval) {
        this.tablets = tablets;
        this.orderCreatingInterval = orderCreatingInterval;
    }

    @Override
    public void run() {
        int randomTablet = (int) (Math.random() * tablets.size());
        System.out.printf("%d - tablets.size; %d - randomTables;", tablets.size(), randomTablet);
        if (tablets.size() > 0) {
//            int k = 5;
            // while (k > 0) {
            while (!Thread.currentThread().isInterrupted()) {
                tablets.get(randomTablet).createTestOrder();
//            k--;
                try {
                    Thread.sleep(orderCreatingInterval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
