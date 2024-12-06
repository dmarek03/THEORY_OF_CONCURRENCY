

import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static int maxElementCount = 5000;
    private final static int iterationsNumber = 10_000;
    private final static int producerNumber = 18_000;
    private final static int consumerNumber = 2000;
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        QueueBuffer<Integer> queueBuffer = new QueueBuffer<>(maxElementCount * 2);
        List<Runnable> runnableList = new ArrayList<>();
        for (int i = 0; i < producerNumber; i++) {
            runnableList.add(new Producer(queueBuffer, iterationsNumber, i, maxElementCount));
        }
        for (int i = 0; i < consumerNumber; i++) {
            runnableList.add(new Consumer(queueBuffer, iterationsNumber, i, maxElementCount));
        }



        runAll(runnableList);
        long endTime = System.nanoTime(); // Zapisz czas zakończenia
        long duration = endTime - startTime; // Oblicz różnicę

        double durationInMillis = duration / 1_000_000_000.0;
        System.out.printf("Czas wykonania: %.6f s\n", durationInMillis);
    }

    private static void runAll(List<Runnable> runnableList) {
        List<Thread> threadList = runnableList.stream()
                .map(Thread::new)
                .toList();

        for (Thread thread : threadList) thread.start();
        try {
            Thread.sleep(1_000);
            for (Thread thread : threadList) thread.interrupt();
            for (Thread thread : threadList) thread.join();
        } catch (InterruptedException e) {
            // empty
        }
    }
}
