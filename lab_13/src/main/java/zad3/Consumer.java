package zad3;


public class Consumer extends Thread {
    private Buffer buff;
    private int size;

    public Consumer(final Buffer buff, final int size) {
        this.buff = buff;
        this.size = size;
    }

    @Override
    public void run() {
        long start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            int item = buff.get();
            System.out.println(item);
        }
        long end = System.nanoTime();
        System.out.println((end - start) + "ns");
        System.exit(0);
    }
}