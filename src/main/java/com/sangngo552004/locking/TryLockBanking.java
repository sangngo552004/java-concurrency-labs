package com.sangngo552004.locking;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockBanking {
    private final Lock lock = new ReentrantLock();
    private int balance = 100;

    public void withdraw(int amount) {
        String threadName = Thread.currentThread().getName();
        try {
            // Thử lấy lock trong vòng 1 giây
            // Nếu sau 1s mà chưa lấy được lock -> trả về false
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                try {
                    System.out.println(threadName + " đã lấy được lock, đang xử lý...");
                    Thread.sleep(3000); // Giả lập xử lý lâu (3s) -> Lâu hơn thời gian chờ (1s)

                    if (balance >= amount) {
                        balance -= amount;
                        System.out.println(threadName + " rút tiền thành công. Số dư: " + balance);
                    } else {
                        System.out.println(threadName + " không đủ tiền.");
                    }
                } finally {
                    lock.unlock(); // QUAN TRỌNG: Luôn unlock trong finally
                    System.out.println(threadName + " đã nhả lock.");
                }
            } else {
                System.out.println(threadName + " -> BỎ CUỘC: Không thể lấy lock sau 1s chờ đợi.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        TryLockBanking account = new TryLockBanking();

        Thread t1 = new Thread(() -> account.withdraw(50), "Thread-1");
        Thread t2 = new Thread(() -> account.withdraw(50), "Thread-2");

        t1.start(); // T1 chạy trước, giữ lock 3s
        t2.start(); // T2 chạy sau, chỉ kiên nhẫn chờ 1s -> Sẽ bỏ cuộc
    }
}
