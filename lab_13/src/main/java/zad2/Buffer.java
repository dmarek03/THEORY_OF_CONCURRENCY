package zad2;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Buffer implements CSProcess {
    public One2OneChannelInt in, out;

    public Buffer(One2OneChannelInt in, One2OneChannelInt out) {
        this.out = out;
        this.in = in;
    }

    public void run() {
        while (true) {
            out.out().write(in.in().read());
        }
    }
}
