package com.sangngo552004.atomic;


import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    // Cách cũ: Dùng biến thường
    private int normalCount = 0;

    // Cách mới: Dùng Atomic
    private AtomicInteger atomicCount = new AtomicInteger(0);

    // Cách 1: Dùng synchronized (An toàn nhưng chậm hơn)
    public synchronized void incrementSync() {
        normalCount++;
    }

    // Cách 2: Dùng Atomic (Không cần từ khóa synchronized -> Nhanh hơn, non-blocking)
    public void incrementAtomic() {
        atomicCount.incrementAndGet();
    }

    public int getNormal() { return normalCount; }
    public int getAtomic() { return atomicCount.get(); }

    public static void main(String[] args) throws InterruptedException {
        AtomicCounter demo = new AtomicCounter();

        // Tạo 1000 thread, mỗi thread cộng 1000 lần
        // Tổng mong đợi: 1,000,000
        Thread[] threads = new Thread[1000];

        for (int i = 0; i < 1000; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    demo.incrementSync();   // Thử đổi cái này thành incrementAtomic()
                    demo.incrementAtomic();
                }
            });
            threads[i].start();
        }

        // Chờ tất cả chạy xong
        for (Thread t : threads) t.join();

        System.out.println("Normal Count (Sync): " + demo.getNormal());
        System.out.println("Atomic Count (CAS):  " + demo.getAtomic());
    }
}
