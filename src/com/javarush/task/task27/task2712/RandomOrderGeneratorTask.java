package com.javarush.task.task27.task2712;

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
        if (tablets.size() > 0) {
            while (!Thread.currentThread().isInterrupted()) {
                int randomTablet = (int) (Math.random() * tablets.size());
                System.out.printf("%d - tablets.size; %d - randomTables;", tablets.size(), randomTablet);
                tablets.get(randomTablet).createTestOrder();
                try {
                    Thread.sleep(orderCreatingInterval);
                } catch (InterruptedException e) {
                    ConsoleHelper.writeMessage("InterruptedException in class RandomOrderGeneratorTask");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
