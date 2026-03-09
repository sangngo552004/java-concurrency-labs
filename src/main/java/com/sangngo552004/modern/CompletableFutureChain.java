package com.sangngo552004.modern;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureChain {
    public static void main(String[] args) {
        System.out.println("Main Thread: Bắt đầu xử lý đơn hàng...");

        CompletableFuture<Void> future =
                CompletableFuture.supplyAsync(() -> {
                            sleep(1000);
                            System.out.println(Thread.currentThread().getName() + " -> Đang kiểm tra kho hàng...");
                            return "iPhone 16 Pro Max";
                        })
                        .thenApply(product -> {
                            System.out.println(Thread.currentThread().getName() + " -> Đang tính thuế cho: " + product);
                            return product + " (Đã tính VAT)";
                        })
                        .thenAccept(finalProduct -> {
                            sleep(500);
                            System.out.println(Thread.currentThread().getName() + " -> Đã gửi email xác nhận cho: " + finalProduct);
                        });

        System.out.println("Main Thread: Không bị chặn, vẫn làm việc khác...");

        future.join(); // CHỜ ĐÚNG CÁCH
    }


    private static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { }
    }
}
