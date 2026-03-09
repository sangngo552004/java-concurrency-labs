package com.sangngo552004.basic;

public class RaceConditionDemo {
    private int count = 0;

    // TODO: Hãy thêm từ khóa 'synchronized' vào method này để fix lỗi
    // public synchronized void increment() {
    public synchronized void increment() {
        count++;
        // Thực tế count++ gồm 3 bước:
        // 1. Read count from memory
        // 2. Add 1
        // 3. Write back to memory
        // -> Khi 2 thread chạy cùng lúc, chúng đè lên nhau.
    }

    public static void main(String[] args) throws InterruptedException {
        RaceConditionDemo demo = new RaceConditionDemo();

        // Thread 1: Tăng 10.000 lần
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                demo.increment();
            }
        });

        // Thread 2: Tăng 10.000 lần
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                demo.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join(); // Chờ t1 chạy xong
        t2.join(); // Chờ t2 chạy xong

        System.out.println("Expected count: 20000");
        System.out.println("Actual count:   " + demo.count);

        if (demo.count != 20000) {
            System.err.println("!! RACE CONDITION DETECTED !!");
        } else {
            System.out.println("Success! Thread safe.");
        }
    }
}
