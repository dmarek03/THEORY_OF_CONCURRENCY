import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Producer implements Runnable {
    private final QueueBuffer<Integer> buffer;
    private final int iterations_number;
    private final int producerID;
    private static final Random random = new Random();
    private static final AtomicInteger producerCount = new AtomicInteger(0);
    private static final Lock lock = new ReentrantLock();
    private static final Condition cleanup = lock.newCondition();
    private final int  maxElementCount;

    public Producer(QueueBuffer<Integer> buffer, int repeat, int id, int M) {
        this.buffer = buffer;
        this.iterations_number = repeat;
        this.producerID = id;
        this.maxElementCount = M;
    }

    private int getRandomElementCount() {
        return random.nextInt(this.maxElementCount) + 1;
    }

    private static List<Integer> getElementList(int count) {
        List<Integer> elementList = new LinkedList<>();
        for (int i = 0; i < count; i++)
            elementList.add(i + 1);
        return elementList;
    }

    @Override
    public void run() {
        producerCount.incrementAndGet();
        for (int i = 0; i < iterations_number; i++) {
            try {
                int elementCount = getRandomElementCount();
                System.out.println("producer[" + producerID + "]: try put " + elementCount);
                buffer.put(getElementList(elementCount));
                System.out.println("producer[" + producerID + "]: put " + elementCount);
            } catch (InterruptedException e) {
                break;
            }
        }
        lock.lock();
        if (producerCount.decrementAndGet() == 0) cleanup.signal();
        lock.unlock();
        System.out.println("producer[" + producerID + "]: exit (" + producerCount.intValue() + " remaining)");
    }
}
