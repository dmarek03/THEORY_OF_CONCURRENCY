package zad1;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    public One2OneChannelInt[] in;
    public int size;

    public Consumer(One2OneChannelInt[] in, int size) {
        this.in = in;
        this.size = size;
    }

    public void run() {
        long start = System.nanoTime();

        final Guard[] guards = new Guard[in.length];

        for (int idx = 0; idx < in.length; idx++) {
            guards[idx] = in[idx].in();
        }

        Alternative alternative = new Alternative(guards);

        for (int idx = 0; idx < size; idx++) {
            int index = alternative.select();
            int item = in[index].in().read();
            System.out.println(index + ": " + item);
        }

        long end = System.nanoTime();

        System.out.println((end - start) + " nanoseconds");
        System.exit(0);
    }
}

