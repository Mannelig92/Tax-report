import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    static LongAdder stat = new LongAdder();

    final static int MAX_PRICE = 100_000;
    static int countOfOperations;

    static Thread threadShop1 = new Thread(Main::sumMoney, "Магазин №1");
    static Thread threadShop2 = new Thread(Main::sumMoney, "Магазин №2");
    static Thread threadShop3 = new Thread(Main::sumMoney, "Магазин №3");

    public static void main(String[] args) {
        threadShop1.start();
        threadShop2.start();
        threadShop3.start();

        try {
            threadShop1.join();
            threadShop2.join();
            threadShop3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sumMoney() {
        stat.add(amountFromOne());
        if (countOfOperations == 3) {
            System.out.printf("Всего денег получено - %s рублей", stat.sum());
        }
    }

    public static Integer amountFromOne() {  // получено денег с одного магазина
        ArrayList<Integer> shop = new ArrayList<>();
        int amountFromOneShop = 0;
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            shop.add(random.nextInt(MAX_PRICE));
        }
        for (Integer i : shop) {
            amountFromOneShop = amountFromOneShop + i;
        }
        System.out.printf("Денег получено с %s - %d рублей\n", Thread.currentThread().getName(), amountFromOneShop);
        countOfOperations++;
        return amountFromOneShop;
    }
}
