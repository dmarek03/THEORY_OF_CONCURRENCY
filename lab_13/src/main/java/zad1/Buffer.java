package zad1;

import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.CSProcess;

public class Buffer implements CSProcess {
    public One2OneChannelInt producer, consumer, still;

    public Buffer(One2OneChannelInt prod, One2OneChannelInt cons, One2OneChannelInt still) {
        this.consumer = cons;
        this.producer = prod;
        this.still = still;
    }

    public void run() {
        while (true) {
            still.out().write(0);
            consumer.out().write(producer.in().read());
        }
    }
}
