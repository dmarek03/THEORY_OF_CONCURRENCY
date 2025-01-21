package zad2;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    public One2OneChannelInt out;
    public int size;

    public Producer(One2OneChannelInt out, int size) {
        this.out = out;
        this.size = size;
    }

    public void run() {
        for (int idx = 0; idx < size; idx++) {
            int item = (int) (Math.random() * 100) + 1;
            out.out().write(item);
        }
    }

}
