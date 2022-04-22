package com.javarush.task.task27.task2712.ad;

import java.util.*;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException {
        if(storage.list().isEmpty()) throw new NoVideoAvailableException();
        List<Advertisement> list = optimalList();
        for(Advertisement ad : list) {
            System.out.printf("%s is displaying... %d, %d\n", ad.getName(), ad.getAmountPerOneDisplaying(), ad.getCostOfOneSecond());
            ad.revalidate();
        }

        long cost = 0;
        long second = 0;
        for (Advertisement ad : list) {
            cost += ad.getAmountPerOneDisplaying();
            second += ad.getDuration();
        }
        System.out.printf("Общая стоимость: %d\nОбщая продолжительность: %d из 1200", cost, second);
    }

    // Рекурсивный метод
//    static int[] w = {5, 3, 1, 6, 7};
//    static int[] v = {1, 8, 18, 22, 28};
//    static int n = 5;
//    static int W = 11;
//
//    static int knapsackRec(int[] w, int[] v, int n, int W) {
//
//        if (n <= 0) {
//            return 0;
//        } else if (w[n-1] > W) {
//            return knapsackRec(w, v, n-1, W);
//        } else {
//            return Math.max(knapsackRec(w, v, n-1, W),
//                    v[n-1] + knapsackRec(w, v, n-1, W-w[n-1]));
//        }
//    }


    // ПОДБОРКА ВИДЕО ДИНАМИЧЕСКИМ СПОСОБОМ

    // Класс для хранения промежуточного списка роликов и их общей стоимости
    public static class TempStorage {
        private List<Advertisement> ads = new ArrayList<>(); // в этом списке будет храниться промежуточный плейлист рекламных роликов,
        // в каждой ячейке двухмерной таблицы будет храниться один объект TempStorage с таким списком
        private long price = 0;
        private int duration = 0;

        public TempStorage(List<Advertisement> ads) {
            if (ads != null) {
                this.ads = ads;
                for (Advertisement ad : ads) {
                    price += ad.getAmountPerOneDisplaying();
                    duration += ad.getDuration();
                }
            }
        }

        public List<Advertisement> getAds() {
            return ads;
        }

        public long getPrice() {
            return price;
        }

        public int getDuration() {
            return duration;
        }
    }

    // Метод для получения "отфильтрованного" от повторов, отсортированного по времени списка с рекламными роликами из хранилища
    private List<Advertisement> getPlayList() {
        LinkedHashSet<Advertisement> linkedPlayList = new LinkedHashSet<>(storage.list()); // список всех рекламных роликов без повторений
        List<Advertisement> playList = new ArrayList<>();
        for(Advertisement ad : linkedPlayList) {
            if (ad.getHits() > 0) playList.add(ad); // превращаем его в ArrayList для удобства сортировки
        }

        playList.sort(new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getDuration() - o2.getDuration();
            }
        }); // сортируем по продолжительности

        return playList;
    }

    // Метод вычисления оптимального списка рекламных роликов с наибольшей стоимостью
    public List<Advertisement> optimalList() {
        List<Advertisement> playList = getPlayList();
        int n = playList.size(); // количество срок в таблице = количество роликов в хранилище рекламы
        int delay = playList.get(0).getDuration() - 1;
        int k = timeSeconds - delay; // количество столбцов = общее время
        if(k < 0) return new ArrayList<>();
        TempStorage[][] bp = new TempStorage[n + 1][k + 1]; // таблица, по строкам - ролики + 1, по столбцам время в секундах + 1.

        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < k + 1; j++) {
                if (i == 0 || j == 0) bp[i][j] = null; // нулевые столбик и строку заполняем нулями
                else if (i == 1) { // добавляем первый рекламный ролик в первую строку
                    List<Advertisement> newList = new ArrayList<Advertisement>();
                    newList.add(playList.get(0));
                    bp[1][j] = new TempStorage(newList);
                } else {
                    if (playList.get(i - 1).getDuration() - delay > j) // если очередной ролик не влезает по времени,
                        bp[i][j] = bp[i -1][j]; // то записываем предыдущий максимум
                    else {
                        long newPrice = bp[i - 1][j - (playList.get(i - 1).getDuration() - delay)] != null ?
                                playList.get(i - 1).getAmountPerOneDisplaying() + bp[i - 1][j - (playList.get(i - 1).getDuration() - delay)].getPrice():
                                playList.get(i - 1).getAmountPerOneDisplaying(); // стоимость Price = сумма стоимости текущего ролика и
                        // стоимости ролика, который поместится в оставшееся для рекламы количество времени (из всего времени j в этом столбце
                        // (каждый столбец - инкремент количества секунд, если можно так выразиться) вычитаем продолжительность текущего ролика
                        if ((bp[i - 1][j] != null) && (bp[i - 1][j].getPrice() > newPrice)) // если стоимость плейлиста в ячейке выше больше,
                            bp[i][j] = bp[i - 1][j]; // то помещаем в текущую ячейку значение из ячейки строчкой выше
                        else {
                            List<Advertisement> newList = bp[i - 1][j - (playList.get(i - 1).getDuration() - delay)] != null ? // иначе помещаем в текущую ячейку
                                    new ArrayList<>(bp[i - 1][j - (playList.get(i - 1).getDuration() - delay)].getAds()) : // новый список, состоящий из списка в ячейке
                                    new ArrayList<>(); // из строчки выше в столбике минус продолжительность текущего ролика и
                            newList.add(playList.get(i - 1)); // текущего ролика.
                            bp[i][j] = new TempStorage(newList);
                        }
                    }
                }
            }
        }

        List<Advertisement> resultList = null;
        long newPrice = 0;
        int newDuration = 0;
        int listSize = 0;

        for (int i = 0; i <= n; i++) { // из последнего столбца выбираем список с наибольшей общей стоимостью price
        if (bp[i][k] != null && bp[i][k].getPrice() > newPrice && bp[i][k].getDuration() <= timeSeconds) { // если стоимость списка из текущей ячейки выше, то сохраняем его в результат
            newPrice = bp[i][k].getPrice();
            newDuration = bp[i][k].getDuration();
            listSize = bp[i][k].getAds().size();

            resultList = bp[i][k].getAds();
        } else if (bp[i][k] != null && bp[i][k].getPrice() == newPrice && bp[i][k].getDuration() <= timeSeconds) { // если стоимости равны, то
            if (bp[i][k].getDuration() > newDuration) resultList = bp[i][k].getAds(); // выбираем самый длинный по времени плейлист.
            else if (bp[i][k].getDuration() == newDuration) // если и длительности равны, то
                resultList = bp[i][k].getAds().size() < listSize ? bp[i][k].getAds() : resultList; // выбираем наименьшее количество роликов
        }
    }

        resultList.sort(comparator);

        return resultList;
    }


//    public List<Advertisement> optimalList() {
//        List<Advertisement> playList = getPlayList();
//        int n = playList.size(); // количество срок в таблице = количество роликов в хранилище рекламы
//        int k = timeSeconds; // количество столбцов = общее время
//        if(k < 0) return new ArrayList<>();
//        TempStorage[][] bp = new TempStorage[n + 1][k + 1]; // таблица, по строкам - ролики + 1, по столбцам время в секундах + 1.
//
//        for (int i = 0; i < n + 1; i++) {
//            for (int j = 0; j < k + 1; j++) {
//                if (i == 0 || j == 0) bp[i][j] = null; // нулевые столбик и строку заполняем нулями
//                else if (i == 1) { // добавляем первый рекламный ролик в первую строку
//                    List<Advertisement> newList = new ArrayList<>();
//                    newList.add(playList.get(0));
//                    bp[1][j] = new TempStorage(newList);
//                } else {
//                    if (playList.get(i - 1).getDuration() > j) // если очередной ролик не влезает по времени,
//                        bp[i][j] = bp[i -1][j]; // то записываем предыдущий максимум
//                    else {
//                        long newPrice = bp[i - 1][j - (playList.get(i - 1).getDuration())] != null ?
//                                playList.get(i - 1).getAmountPerOneDisplaying() + bp[i - 1][j - (playList.get(i - 1).getDuration())].getPrice():
//                                playList.get(i - 1).getAmountPerOneDisplaying(); // стоимость Price = сумма стоимости текущего ролика и
//                        // стоимости ролика, который поместится в оставшееся для рекламы количество времени (из всего времени j в этом столбце
//                        // (каждый столбец - инкремент количества секунд, если можно так выразиться) вычитаем продолжительность текущего ролика
//                        if ((bp[i - 1][j] != null) && (bp[i - 1][j].getPrice() > newPrice)) // если стоимость плейлиста в ячейке выше больше,
//                            bp[i][j] = bp[i - 1][j]; // то помещаем в текущую ячейку значение из ячейки строчкой выше
//                        else {
//                            List<Advertisement> newList = bp[i - 1][j - (playList.get(i - 1).getDuration())] != null ? // иначе помещаем в текущую ячейку
//                                    new ArrayList<>(bp[i - 1][j - (playList.get(i - 1).getDuration())].getAds()) : // новый список, состоящий из списка в ячейке
//                                    new ArrayList<>(); // из строчки выше в столбике минус продолжительность текущего ролика и
//                            newList.add(playList.get(i - 1)); // текущего ролика.
//                            bp[i][j] = new TempStorage(newList);
//                        }
//                    }
//                }
//            }
//        }
//
//        List<Advertisement> resultList = null;
//        long newPrice = 0;
//        int newDuration = 0;
//        int listSize = 0;
//
//        for (int i = 0; i <= n; i++) { // из последнего столбца выбираем список с наибольшей общей стоимостью price
//            if (bp[i][k] != null && bp[i][k].getPrice() > newPrice && bp[i][k].getDuration() <= timeSeconds) { // если стоимость списка из текущей ячейки выше, то сохраняем его в результат
//                newPrice = bp[i][k].getPrice();
//                newDuration = bp[i][k].getDuration();
//                listSize = bp[i][k].getAds().size();
//
//                resultList = bp[i][k].getAds();
//            } else if (bp[i][k] != null && bp[i][k].getPrice() == newPrice && bp[i][k].getDuration() <= timeSeconds) { // если стоимости равны, то
//                if (bp[i][k].getDuration() > newDuration) resultList = bp[i][k].getAds(); // выбираем самый длинный по времени плейлист.
//                else if (bp[i][k].getDuration() == newDuration) // если и длительности равны, то
//                    resultList = bp[i][k].getAds().size() < listSize ? bp[i][k].getAds() : resultList; // выбираем наименьшее количество роликов
//            }
//        }
//
//        resultList.sort(comparator);
//
//        return resultList;
//    }


    // Компаратор для сравнения объектов по уменьшению стоимости ролика, либо (вторичная сортировка) по увеличению стоимости одной секунды ролика
    private Comparator<Advertisement> comparator = Collections.reverseOrder(new Comparator<Advertisement>() {
        @Override
        public int compare(Advertisement o1, Advertisement o2) {
            Long amount1 = o1.getAmountPerOneDisplaying();
            Long amount2 = o2.getAmountPerOneDisplaying();
            if (!amount1.equals(amount2))
                return amount1.compareTo(amount2);

            Long amountDuration1 = o1.getCostOfOneSecond();
            Long amountDuration2 = o2.getCostOfOneSecond();

            return amountDuration2.compareTo(amountDuration1);
        }
    });
}
