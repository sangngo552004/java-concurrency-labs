package com.sangngo552004.threadpool;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledJob {
    public static void main(String[] args) {
        // Tạo một scheduler với 1 thread duy nhất
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        System.out.println("Hệ thống khởi động lúc: " + System.currentTimeMillis());

        // 1. Task chạy 1 lần duy nhất sau 3 giây (One-shot)
        scheduler.schedule(() -> {
            System.out.println("LOG: 3 giây đã trôi qua. Task 1 lần chạy xong.");
        }, 3, TimeUnit.SECONDS);

        // 2. Task chạy định kỳ (Recurring)
        // initialDelay: Chờ 1s mới bắt đầu chạy lần đầu
        // period: Cứ cách 2s lại chạy một lần (Tính từ lúc bắt đầu task trước)
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("HEARTBEAT: " + System.currentTimeMillis() + " - Server vẫn sống!");
        }, 1, 2, TimeUnit.SECONDS);

        // Để demo chạy trong 10 giây rồi tắt
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Hết giờ demo. Tắt hệ thống.");
        scheduler.shutdown();
    }
}
