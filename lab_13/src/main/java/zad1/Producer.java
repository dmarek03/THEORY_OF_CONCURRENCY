package zad1;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    public One2OneChannelInt[] out;
    public One2OneChannelInt[] buffer;
    public int size;

    public Producer(One2OneChannelInt[] out, One2OneChannelInt[] buffer, int size) {
        this.out = out;
        this.buffer = buffer;
        this.size = size;
    }

    public void run() {
        final Guard[] guards = new Guard[buffer.length];

        for (int idx = 0; idx < out.length; idx++) {
            guards[idx] = buffer[idx].in();
        }

        Alternative alternative = new Alternative(guards);

        for (int idx = 0; idx < size; idx++) {
            int index = alternative.select();
            buffer[index].in().read();
            out[index].out().write((int) ((Math.random() * 100) + 1));
        }
    }

}
