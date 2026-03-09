package com.sangngo552004.atomic;

import java.util.concurrent.locks.StampedLock;

public class OptimisticStamper {
    private double x, y;
    private final StampedLock sl = new StampedLock();

    // Writer: Chiếm quyền ghi (Exclusive Lock)
    public void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock(); // Lấy con dấu ghi
        try {
            x += deltaX;
            y += deltaY;
            System.out.println("Writer: Đã di chuyển tới (" + x + ", " + y + ")");
        } finally {
            sl.unlockWrite(stamp); // Trả dấu
        }
    }

    // Reader: Optimistic (Lạc quan)
    public double distanceFromOrigin() {
        // 1. Lấy một con dấu "lạc quan" (Không hề chặn ai cả!)
        long stamp = sl.tryOptimisticRead();

        // 2. Copy dữ liệu ra biến cục bộ
        double currentX = x;
        double currentY = y;

        // 3. KIỂM TRA LẠI: Trong lúc tôi đang copy, có ai ghi vào không?
        if (!sl.validate(stamp)) {
            // 4. Xui quá, có người ghi đè rồi.
            // Thôi chuyển sang dùng ReadLock "bi quan" truyền thống cho chắc ăn.
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }

        // Tính toán nặng nề dựa trên dữ liệu đã copy
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    public static void main(String[] args) {
        OptimisticStamper point = new OptimisticStamper();

        // Thread Writer: Di chuyển điểm sau 1s
        new Thread(() -> {
            try { Thread.sleep(1000); } catch (Exception e) {}
            point.move(10, 10);
        }).start();

        // Thread Reader: Đọc liên tục
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                double dist = point.distanceFromOrigin();
                System.out.println("Reader: Khoảng cách = " + dist);
                try { Thread.sleep(300); } catch (Exception e) {}
            }
        }).start();
    }
}
