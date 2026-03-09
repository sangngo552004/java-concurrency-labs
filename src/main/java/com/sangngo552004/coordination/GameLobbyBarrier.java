package com.sangngo552004.coordination;

import java.util.concurrent.CyclicBarrier;

public class GameLobbyBarrier {
    public static void main(String[] args) {
        // Barrier chờ 3 người. Khi đủ 3 người, chạy hành động "Bắt đầu game"
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println(">> TRỌNG TÀI: Đủ 3 người rồi. GAME START! <<");
        });

        for (int i = 1; i <= 3; i++) {
            new Thread(new Player("Player " + i, barrier)).start();
        }
    }

    static class Player implements Runnable {
        private String name;
        private CyclicBarrier barrier;

        public Player(String name, CyclicBarrier barrier) {
            this.name = name;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " đang tải map...");
                Thread.sleep((long) (Math.random() * 3000)); // Thời gian tải ngẫu nhiên
                System.out.println(name + " đã tới phòng chờ. Đợi người khác...");

                // Đứng đợi ở đây. Nếu chưa đủ 3 người thì không đi tiếp.
                barrier.await();

                System.out.println(name + " bắt đầu chơi!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
