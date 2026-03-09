# Java Concurrency Labs

This repository contains a practical collection of examples demonstrating various concepts of concurrency and multi-threading in Java. It covers everything from basic thread safety issues to modern Java 21 features like Virtual Threads.

## 🛠 Prerequisites
* **Java SDK:** Version 21
* **Build Tool:** Maven

## 📂 Project Structure

The project is structured into multiple packages under `com.sangngo552004`, each focusing on a specific concurrency topic:

### 1. Basic Concepts (`com.sangngo552004.basic`)
* **`RaceConditionDemo`**: Demonstrates race conditions during shared counter increments and explains how to fix them using the `synchronized` keyword.
* **`VisibilityDemo`**: Shows memory visibility issues across different threads and demonstrates the usage of the `volatile` keyword to ensure thread-safe variable state checking.

### 2. Atomic Variables & Locks (`com.sangngo552004.atomic`)
* **`AtomicCounter`**: Compares the performance and structure of traditional synchronization versus the non-blocking `AtomicInteger` (CAS).
* **`OptimisticStamper`**: Explains optimistic reading mechanisms without blocking using Java's `StampedLock`.

### 3. Thread Coordination (`com.sangngo552004.coordination`)
* **`CountDownLatch` (`ServerStartupLatch`)**: Simulates a server startup sequence where the main thread waits for multiple sub-services (Database, Cache, Messaging) to initialize before proceeding.
* **`CyclicBarrier` (`GameLobbyBarrier`)**: Simulates a game lobby that waits until exactly 3 players are ready before starting the game.
* **`Phaser` (`PhaserBasicDemo`)**: Demonstrates multi-phase synchronization where threads wait for each other at various stages (Load data, Validate, Process).
* **`Producer-Consumer` (`ProducerConsumerQueue`)**: Implements the classic Producer-Consumer pattern using a thread-safe `ArrayBlockingQueue`.

### 4. Advanced Locking (`com.sangngo552004.locking`)
* **`ReadWriteCache`**: Shows how to improve read performance using `ReentrantReadWriteLock` by allowing multiple concurrent readers but exclusive writers.
* **`TryLockBanking`**: Demonstrates deadlock prevention and timeout handling using the `tryLock()` feature of `ReentrantLock`.

### 5. Thread Pools (`com.sangngo552004.threadpool`)
* **`PoolvsNewThread`**: Highlights the efficiency of reusing threads using an `ExecutorService` (`FixedThreadPool`) compared to creating new threads manually.
* **`ScheduledJob`**: Uses `ScheduledExecutorService` to execute one-shot delayed tasks and recurring periodic tasks.

### 6. Modern Concurrency (`com.sangngo552004.modern`)
* **`CompletableFutureChain`**: Demonstrates asynchronous non-blocking programming by chaining multiple tasks (inventory check, tax calculation, email confirmation).
* **`VirtualThreadDemo`**: Showcases the power of Java 21's Virtual Threads by effortlessly spawning 100,000 tasks concurrently without overwhelming the OS or causing OutOfMemory errors.

## 🚀 How to Run
You can run any of the demonstrations by executing their respective `main` methods directly from your IDE (IntelliJ IDEA, Eclipse, VS Code).
