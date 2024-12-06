package zad1.semaphore;

public class Main {
    private final int readersAmount;
    private final int writersAmount;

    public Main(int readersAmount, int writersAmount) {
        this.readersAmount = readersAmount;
        this.writersAmount = writersAmount;
    }

    public static void main(String[] args) {
        Main tmp;
        for (int writersAmount = 1; writersAmount <= 10; ++writersAmount) {
            for (int readersAmount = 10; readersAmount <= 100; readersAmount += 5) {
                tmp = new Main(readersAmount, writersAmount);
                tmp.run();
            }
        }
    }

    public void run() {
        final Library library = new Library();
        Reader[] reader = new Reader[readersAmount];
        Writer[] writer = new Writer[writersAmount];

        for (int i = 0; i < readersAmount; ++i) {
            reader[i] = new Reader(i, library);
            reader[i].start();
        }

        for (int i = 0; i < writersAmount; ++i) {
            writer[i] = new Writer(i, library);
            writer[i].start();
        }

        for (int i = 0; i < readersAmount; ++i) {
            try {
                reader[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < writersAmount; ++i) {
            try {
                writer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(String.format("%d; %d; %.2f; %.2f",
                readersAmount,
                writersAmount,
                library.getReaderWaitingTime() / (double) readersAmount / 1_000_000, // Średni czas w ms
                library.getWriterWaitingTime() / (double) writersAmount / 1_000_000)); // Średni czas w ms
    }
}
