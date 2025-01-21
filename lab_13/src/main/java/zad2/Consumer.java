package zad2;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    public One2OneChannelInt in;
    public int size;

    public Consumer( One2OneChannelInt in,  int size) {
        this.in = in;
        this.size = size;
    }

    public void run() {
        long start = System.nanoTime();

        for (int idx = 0; idx < size; idx++) {
            int item = in.in().read();
            System.out.println(item);
        }

        long end = System.nanoTime();
        System.out.println((end - start) + " nanoseconds");
        System.exit(0);
    }
}

