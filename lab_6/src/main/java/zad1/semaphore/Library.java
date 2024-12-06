package zad1.semaphore;

import java.util.concurrent.Semaphore;

public class Library {
    private int readCount = 0; // Liczba aktywnych czytelników
    private Semaphore resource = new Semaphore(1); // Dostęp do zasobu
    private Semaphore rMutex = new Semaphore(1); // Kontrola dostępu do licznika czytelników
    private Semaphore serviceQueue = new Semaphore(1); // Kolejka, aby zachować sprawiedliwość

    // Prywatne zmienne przechowujące sumaryczny czas oczekiwania
    private long readerWaitingTime = 0L;
    private long writerWaitingTime = 0L;

    public void beginReading() {
        long startTime = System.nanoTime(); // Rozpoczęcie pomiaru czasu oczekiwania
        try {
            serviceQueue.acquire(); // Czytelnik ustawia się w kolejce

            rMutex.acquire(); // Zabezpieczenie licznika
            readCount++;
            if (readCount == 1) {
                resource.acquire(); // Jeśli pierwszy czytelnik, zablokuj zasób
            }
            rMutex.release();
            serviceQueue.release(); // Czytelnik wychodzi z kolejki

            // Dodanie czasu oczekiwania do zmiennej readerWaitingTime
            setReaderWaitingTime(getReaderWaitingTime() + (System.nanoTime() - startTime));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void endReading() {
        try {
            rMutex.acquire();
            readCount--;
            if (readCount == 0) {
                resource.release(); // Jeśli ostatni czytelnik, zwolnij zasób
            }
            rMutex.release();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void beginWriting() {
        long startTime = System.nanoTime(); // Rozpoczęcie pomiaru czasu oczekiwania
        try {
            serviceQueue.acquire(); // Pisarz ustawia się w kolejce
            resource.acquire(); // Blokada zasobu dla pisarza
            serviceQueue.release(); // Pisarz wychodzi z kolejki

            // Dodanie czasu oczekiwania do zmiennej writerWaitingTime
            setWriterWaitingTime(getWriterWaitingTime() + (System.nanoTime() - startTime));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void endWriting() {
        resource.release(); // Zwolnienie zasobu po zakończeniu pisania
    }

    // Getter i setter dla readerWaitingTime
    public long getReaderWaitingTime() {
        return readerWaitingTime;
    }

    public void setReaderWaitingTime(long readerWaitingTime) {
        this.readerWaitingTime = readerWaitingTime;
    }

    // Getter i setter dla writerWaitingTime
    public long getWriterWaitingTime() {
        return writerWaitingTime;
    }

    public void setWriterWaitingTime(long writerWaitingTime) {
        this.writerWaitingTime = writerWaitingTime;
    }
}
