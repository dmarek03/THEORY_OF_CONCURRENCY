package zad1;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;
import org.jcsp.lang.StandardChannelIntFactory;

public class Main {
    static final int buffers = 20;
    static final int items = 100000;

    public static void main(String[] args) {
        StandardChannelIntFactory factory = new StandardChannelIntFactory();

        One2OneChannelInt[] channelProducer = factory.createOne2One(buffers);
        One2OneChannelInt[] channelBuffer = factory.createOne2One(buffers);
        One2OneChannelInt[] channelConsumer = factory.createOne2One(buffers);

        CSProcess[] processes = new CSProcess[buffers + 2];

        processes[0] = new Producer(channelProducer, channelBuffer, items);
        processes[1] = new Consumer(channelConsumer, items);

        for (int idx = 2; idx < buffers + 2; idx++) {
            processes[idx] = new Buffer(channelProducer[idx - 2], channelConsumer[idx - 2], channelBuffer[idx - 2]);
        }

        Parallel parallel = new Parallel(processes);
        parallel.run();
    }
}
