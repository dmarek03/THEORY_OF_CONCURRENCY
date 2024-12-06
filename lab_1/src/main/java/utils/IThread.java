package utils;

// Watek, ktory inkrementuje licznik 100.000 razy
class IThread extends Thread {
    private Counter counter;

    public IThread(Counter counter) {
        this.counter = counter;

    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            counter.inc();
        }

    }

}