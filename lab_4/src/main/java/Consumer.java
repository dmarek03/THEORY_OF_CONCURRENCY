import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable {
    private final QueueBuffer<Integer> buffer;
    private final int iterations_number;
    private final int consumerID;
    private static final Random random = new Random();
    private static final AtomicInteger consumerCount = new AtomicInteger(0);
    private static final Lock lock = new ReentrantLock();
    private static final Condition cleanup = lock.newCondition();
    private final int maxElementCount;

    public Consumer(QueueBuffer<Integer> buffer, int repeat, int id,int M) {
        this.buffer = buffer;
        this.iterations_number = repeat;
        this.consumerID = id;
        this.maxElementCount = M;
    }

    private int getRandomElementCount() {
        return random.nextInt(maxElementCount) + 1;
    }

    @Override
    public void run() {
        consumerCount.incrementAndGet();
        for (int i = 0; i < iterations_number; i++) {
            try {
                int elementCount = getRandomElementCount();
                System.out.println("consumer[" + consumerID + "]: try take " + elementCount);
                buffer.take(elementCount);
                System.out.println("consumer[" + consumerID + "]: taken " + elementCount);
            } catch (InterruptedException e) {
                break;
            }
        }
        lock.lock();
        if (consumerCount.decrementAndGet() == 0) cleanup.signal();
        lock.unlock();
        System.out.println("consumer[" + consumerID + "]: exit (" + consumerCount.intValue() + " remaining)");
    }
}
