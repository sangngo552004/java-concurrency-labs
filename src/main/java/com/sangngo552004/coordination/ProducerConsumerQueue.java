package com.sangngo552004.coordination;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerQueue {
    public static void main(String[] args) {
        // Giới hạn hàng đợi chỉ chứa tối đa 2 món
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

        // Ông đầu bếp (Producer)
        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    String food = "Món ăn #" + i;
                    System.out.println("Đầu bếp: Đang nấu " + food);
                    // put() sẽ BLOCK nếu hàng đợi đầy
                    queue.put(food);
                    System.out.println("Đầu bếp: Đã để " + food + " lên bàn.");
                    Thread.sleep(200); // Nấu nhanh
                }
            } catch (InterruptedException e) { }
        }).start();

        // Ông khách (Consumer)
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000); // Ăn chậm
                    // take() sẽ BLOCK nếu hàng đợi rỗng
                    String food = queue.take();
                    System.out.println("Khách: Đã ăn xong " + food);
                }
            } catch (InterruptedException e) { }
        }).start();
    }
}
