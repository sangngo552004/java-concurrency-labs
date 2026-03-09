package com.sangngo552004.modern;


import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualThreadDemo {
    public static void main(String[] args) {
        // Thử tạo 100,000 thread.
        // Nếu dùng newFixedThreadPool (Platform Thread), máy bạn có thể bị treo hoặc OOM.
        int taskCount = 100_000;

        System.out.println("Bắt đầu chạy " + taskCount + " tác vụ với Virtual Threads...");
        Instant start = Instant.now();

        // Executor mới trong Java 21 dành riêng cho Virtual Thread
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, taskCount).forEach(i -> {
                executor.submit(() -> {
                    Instant t1 = Instant.now();
                    try {
                        Thread.sleep(Duration.ofMillis(50));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Instant t2 = Instant.now();
                    System.out.println("Task " + i + " slept: " + Duration.between(t1,t2).toMillis() + "ms");
                });

            });
        } // Tự động chờ tất cả task xong (try-with-resources)

        Instant end = Instant.now();
        System.out.println("Hoàn thành trong: " + Duration.between(start, end).toMillis() + "ms");
        System.out.println("Core CPU vẫn mát rượi!");
    }
}
