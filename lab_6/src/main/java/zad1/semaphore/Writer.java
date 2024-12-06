package zad1.semaphore;

public class Writer extends Thread {
    private int nr;
    private Library library;

    public Writer(int nr, Library library) {
        super();
        this.nr = nr;
        this.library = library;
    }

    @Override
    public void run() {
        int i = 0;
        while (i++ < 1000) {
            library.beginWriting();
            library.endWriting();
        }
    }
}
