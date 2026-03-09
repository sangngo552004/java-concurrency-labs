package com.sangngo552004.coordination;

import java.util.concurrent.Phaser;

public class PhaserBasicDemo {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(3); // đăng ký sẵn 3 worker

        for (int i = 1; i <= 3; i++) {
            new Thread(new Worker(i, phaser)).start();
        }
    }

    static class Worker implements Runnable {
        private final int id;
        private final Phaser phaser;

        Worker(int id, Phaser phaser) {
            this.id = id;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            doPhase("Load data");
            phaser.arriveAndAwaitAdvance(); // phase 0 -> 1

            doPhase("Validate");
            phaser.arriveAndAwaitAdvance(); // phase 1 -> 2

            doPhase("Process");
            phaser.arriveAndAwaitAdvance(); // phase 2 -> 3

            System.out.println("Worker " + id + " DONE");
        }

        private void doPhase(String name) {
            System.out.println("Worker " + id + " đang " + name);
            try {
                Thread.sleep(1000 + id * 300);
            } catch (InterruptedException ignored) {}
        }
    }
}

