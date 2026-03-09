package com.sangngo552004.basic;

public class VisibilityDemo {
    // TODO: Thêm từ khóa 'volatile' để fix lỗi này
    // private volatile boolean running = true;
    private boolean running = true;

    public void startWorker() {
        System.out.println("Worker thread started...");
        while (running) {
        }
        System.out.println("Worker thread stopped!");
    }

    public void stopWorker() {
        this.running = false;
        System.out.println("Main thread set running = false");
    }

    public static void main(String[] args) throws InterruptedException {
        VisibilityDemo demo = new VisibilityDemo();

        // Thread phụ chạy vòng lặp
        new Thread(demo::startWorker).start();

        // Main thread ngủ 1 giây rồi ra lệnh dừng
        Thread.sleep(1000);
        demo.stopWorker();
    }
}
