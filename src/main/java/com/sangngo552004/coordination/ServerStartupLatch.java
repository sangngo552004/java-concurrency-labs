package com.sangngo552004.coordination;

import java.util.concurrent.CountDownLatch;

public class ServerStartupLatch {
    public static void main(String[] args) throws InterruptedException {
        // Giả sử cần khởi động 3 dịch vụ con
        int services = 3;
        CountDownLatch latch = new CountDownLatch(services);

        System.out.println("SERVER: Đang khởi động hệ thống...");

        // Tạo 3 thread khởi động song song
        new Thread(new ServiceWorker("Database", 2000, latch)).start();
        new Thread(new ServiceWorker("Cache", 1000, latch)).start();
        new Thread(new ServiceWorker("Messaging", 3000, latch)).start();

        // Main thread bị chặn ở đây cho đến khi latch đếm về 0
        latch.await();

        System.out.println("SERVER: Tất cả dịch vụ đã sẵn sàng. Bắt đầu nhận request!");
    }

    static class ServiceWorker implements Runnable {
        private String name;
        private int timeToStart;
        private CountDownLatch latch;

        public ServiceWorker(String name, int timeToStart, CountDownLatch latch) {
            this.name = name;
            this.timeToStart = timeToStart;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(timeToStart);
                System.out.println(name + " -> Đã khởi động xong.");
                // Quan trọng: Giảm bộ đếm đi 1
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


