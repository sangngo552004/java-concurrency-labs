package com.sangngo552004.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolvsNewThread {

    public static void main(String[] args) {
        // Tạo một hồ chứa chỉ có đúng 3 nhân viên cố định
        // Dù có gửi 1000 task vào, cũng chỉ có 3 ông này thay phiên nhau chạy.
        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("Bắt đầu gửi 10 công việc...");

        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " đang làm task #" + taskId);

                try {
                    Thread.sleep(1000); // Giả lập công việc tốn 1 giây
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println(threadName + " -> XONG task #" + taskId);
            });
        }

        // QUAN TRỌNG: Phải shutdown, nếu không chương trình sẽ chạy mãi không dừng
        executor.shutdown();
        System.out.println("Đã gửi hết task vào hàng đợi (Queue).");
    }
}
