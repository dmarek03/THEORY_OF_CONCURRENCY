package zad2;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;
import org.jcsp.lang.StandardChannelIntFactory;

public class Main {
    static final int buffers = 20;
    static final int items = 100000;

    public static void main(String[] args) {
        StandardChannelIntFactory factory = new StandardChannelIntFactory();
        One2OneChannelInt[] channels = factory.createOne2One(buffers + 1);
        CSProcess[] processes = new CSProcess[buffers + 2];

        processes[0] = new Producer(channels[0], items);
        processes[1] = new Consumer(channels[buffers], items);

        for (int idx = 2; idx < buffers + 2; idx++) {
            processes[idx] = new Buffer(channels[idx - 2], channels[idx - 1]);
        }

        Parallel parallel = new Parallel(processes);
        parallel.run();
    }
}