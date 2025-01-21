package zad3;

public final class Main {
    public static void main(String[] args) {
        final int nBufferPlaces = 10;
        final int nItems = 10000;
        Buffer buff = new Buffer(nBufferPlaces);
        Consumer c = new Consumer(buff, nItems);
        Producer p = new Producer(buff, nItems);
        c.start();
        p.start();
    }
}

