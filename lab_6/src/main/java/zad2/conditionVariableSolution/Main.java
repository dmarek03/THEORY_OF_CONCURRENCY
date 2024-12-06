package zad2.conditionVariableSolution;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private final int readersAmount;
    private final int writersAmount;

    public Main(int readersAmount, int writersAmount) {
        this.readersAmount = readersAmount;
        this.writersAmount = writersAmount;
    }

    public static void main(String[] args) {
        Main tmp;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))) {
            // Zapisujemy nagłówek pliku CSV
            writer.write("readersAmount,writersAmount,readerAvgWaitTime,writerAvgWaitTime");
            writer.newLine();

            for (int writersAmount = 1; writersAmount <= 10; ++writersAmount) {
                for (int readersAmount = 10; readersAmount <= 100; readersAmount += 5) {
                    tmp = new Main(readersAmount, writersAmount);
                    tmp.run(writer);  // Przekazujemy BufferedWriter do metody run
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(BufferedWriter writer) {
        final Library library = new Library();
        Reader[] reader = new Reader[readersAmount];
        Writer[] writerThread = new Writer[writersAmount];

        // Tworzymy i uruchamiamy wątki czytelników
        for (int i = 0; i < readersAmount; ++i) {
            reader[i] = new Reader(i, library);
            reader[i].start();
        }

        // Tworzymy i uruchamiamy wątki pisarzy
        for (int i = 0; i < writersAmount; ++i) {
            writerThread[i] = new Writer(i, library);
            writerThread[i].start();
        }

        // Czekamy na zakończenie wątków
        for (int i = 0; i < readersAmount; ++i) {
            try {
                reader[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < writersAmount; ++i) {
            try {
                writerThread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Obliczamy średni czas oczekiwania
        double readerAvgWaitTime = library.getReaderWaitingTime() / (double) readersAmount / 1_000_000; // Czas w ms
        double writerAvgWaitTime = library.getWriterWaitingTime() / (double) writersAmount / 1_000_000; // Czas w ms

        // Wyświetlamy dane w konsoli
        System.out.println(String.format("%d; %d; %.2f; %.2f",
                readersAmount,
                writersAmount,
                readerAvgWaitTime,
                writerAvgWaitTime));

        // Zapisujemy dane do pliku CSV
        try {
            writer.write(String.format("%d,%d,%.2f,%.2f",
                    readersAmount,
                    writersAmount,
                    readerAvgWaitTime,
                    writerAvgWaitTime));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
