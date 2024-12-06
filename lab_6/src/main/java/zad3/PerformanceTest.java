package zad3;

public class PerformanceTest {
    public static void main(String[] args) {
        FineGrainedLockingList fineGrainedList = new FineGrainedLockingList();
        CoarseGrainedLockingList coarseGrainedList = new CoarseGrainedLockingList();

        // Definiowanie liczb wątków do testowania
        int[] threadCounts = {1, 5, 10, 25, 50, 100};

        // Wykonanie testów dla operacji add, contains i remove
        for (int threadCount : threadCounts) {
            System.out.println("Testing with " + threadCount + " threads");

            // Test dla operacji add na obu listach
            testAddPerformance(fineGrainedList, coarseGrainedList, threadCount);

            // Test dla operacji contains na obu listach
            testContainsPerformance(fineGrainedList, coarseGrainedList, threadCount);

            // Test dla operacji remove na obu listach
            testRemovePerformance(fineGrainedList, coarseGrainedList, threadCount);

            System.out.println("----------------------------------");
        }
    }

    // Test wydajności operacji add
    private static void testAddPerformance(FineGrainedLockingList fineGrainedList, CoarseGrainedLockingList coarseGrainedList, int threadCount) {
        Runnable addTaskFine = () -> {
            for (int i = 0; i < 1000; i++) {
                fineGrainedList.add(i);
            }
        };

        Runnable addTaskCoarse = () -> {
            for (int i = 0; i < 1000; i++) {
                coarseGrainedList.add(i);
            }
        };

        // Test dla Fine-Grained List
        long startTime = System.nanoTime();
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(addTaskFine);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000.0;
        System.out.println("Add operation time (Fine-grained): " + durationInSeconds + " milliseconds");

        // Test dla Coarse-Grained List
        startTime = System.nanoTime();
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(addTaskCoarse);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = System.nanoTime();
        durationInSeconds = (endTime - startTime) / 1_000_000.0;
        System.out.println("Add operation time (Coarse-grained): " + durationInSeconds + " milliseconds");
    }

    // Test wydajności operacji contains
    private static void testContainsPerformance(FineGrainedLockingList fineGrainedList, CoarseGrainedLockingList coarseGrainedList, int threadCount) {
        Runnable containsTaskFine = () -> {
            for (int i = 0; i < 1000; i++) {
                fineGrainedList.contains(i);
            }
        };

        Runnable containsTaskCoarse = () -> {
            for (int i = 0; i < 1000; i++) {
                coarseGrainedList.contains(i);
            }
        };

        // Test dla Fine-Grained List
        long startTime = System.nanoTime();
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(containsTaskFine);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000.0;
        System.out.println("Contains operation time (Fine-grained): " + durationInSeconds + " milliseconds");

        // Test dla Coarse-Grained List
        startTime = System.nanoTime();
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(containsTaskCoarse);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = System.nanoTime();
        durationInSeconds = (endTime - startTime) / 1_000_000.0;
        System.out.println("Contains operation time (Coarse-grained): " + durationInSeconds + " milliseconds");
    }

    // Test wydajności operacji remove
    private static void testRemovePerformance(FineGrainedLockingList fineGrainedList, CoarseGrainedLockingList coarseGrainedList, int threadCount) {
        Runnable removeTaskFine = () -> {
            for (int i = 0; i < 1000; i++) {
                fineGrainedList.remove(i);
            }
        };

        Runnable removeTaskCoarse = () -> {
            for (int i = 0; i < 1000; i++) {
                coarseGrainedList.remove(i);
            }
        };

        // Test dla Fine-Grained List
        long startTime = System.nanoTime();
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(removeTaskFine);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000.0;
        System.out.println("Remove operation time (Fine-grained): " + durationInSeconds + " milliseconds");

        // Test dla Coarse-Grained List
        startTime = System.nanoTime();
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(removeTaskCoarse);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = System.nanoTime();
        durationInSeconds = (endTime - startTime) / 1_000_000.0;
        System.out.println("Remove operation time (Coarse-grained): " + durationInSeconds + " milliseconds");
    }
}
