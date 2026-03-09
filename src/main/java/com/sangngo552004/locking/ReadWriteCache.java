package com.sangngo552004.locking;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteCache {
    private final Map<String, String> cache = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, String value) {
        rwLock.writeLock().lock(); // CHẶN TẤT CẢ (Cả đọc lẫn ghi)
        try {
            System.out.println(Thread.currentThread().getName() + " đang GHI dữ liệu...");
            Thread.sleep(2000); // Giả lập ghi lâu
            cache.put(key, value);
            System.out.println(Thread.currentThread().getName() + " -> GHI XONG.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public String get(String key) {
        rwLock.readLock().lock(); // KHÔNG CHẶN các thread đọc khác
        try {
            System.out.println(Thread.currentThread().getName() + " đang ĐỌC dữ liệu...");
            Thread.sleep(1000); // Giả lập đọc cũng tốn thời gian
            System.out.println(Thread.currentThread().getName() + " -> ĐỌC XONG: " + cache.get(key));
            return cache.get(key);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteCache demo = new ReadWriteCache();

        // 1. Ghi dữ liệu trước
        new Thread(() -> demo.put("java", "Java Concurrency"), "Writer").start();

        // 2. Tạo 3 thread đọc chạy gần như cùng lúc
        for (int i = 1; i <= 3; i++) {
            new Thread(() -> demo.get("java"), "Reader-" + i).start();
        }
    }
}
