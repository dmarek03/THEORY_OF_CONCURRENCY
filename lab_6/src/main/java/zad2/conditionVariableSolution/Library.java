package zad2.conditionVariableSolution;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Library {
    private Lock libraryLock = new ReentrantLock();
    private Condition readers = libraryLock.newCondition();
    private Condition writers = libraryLock.newCondition();

    private int isReading = 0;
    private int isWriting = 0;
    private int writerWaiting = 0;
    private int readerWaiting = 0;

    // Zmienne do przechowywania sumarycznego czasu oczekiwania
    private long readerWaitingTime = 0L;
    private long writerWaitingTime = 0L;

    public void beginReading() {
        long startTime = System.nanoTime();
        libraryLock.lock();
        try {
            while (writerWaiting > 0 || isWriting > 0) {
                ++readerWaiting;
                readers.await();
            }
            // Dodaj czas oczekiwania do zmiennej readerWaitingTime
            readerWaitingTime += System.nanoTime() - startTime;
            isReading += 1;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            libraryLock.unlock();
        }
    }

    public void endReading() {
        libraryLock.lock();
        try {
            isReading -= 1;
            if (isReading == 0) {
                if (writerWaiting > 0) {
                    --writerWaiting;
                }
                writers.signal();
            }
        } finally {
            libraryLock.unlock();
        }
    }

    public void beginWriting() {
        long startTime = System.nanoTime();
        libraryLock.lock();
        try {
            while (isReading + isWriting > 0) {
                ++writerWaiting;
                writers.await();
            }
            // Dodaj czas oczekiwania do zmiennej writerWaitingTime
            writerWaitingTime += System.nanoTime() - startTime;
            isWriting = 1;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            libraryLock.unlock();
        }
    }

    public void endWriting() {
        libraryLock.lock();
        try {
            isWriting = 0;
            if (readerWaiting == 0) {
                if (writerWaiting > 0) {
                    --writerWaiting;
                }
                writers.signal();
            } else {
                readerWaiting = 0;
                readers.signalAll();
            }
        } finally {
            libraryLock.unlock();
        }
    }

    // Metody dostępowe dla readerWaitingTime i writerWaitingTime
    public long getReaderWaitingTime() {
        return readerWaitingTime;
    }

    public long getWriterWaitingTime() {
        return writerWaitingTime;
    }
}
