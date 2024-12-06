import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueBuffer<T> {
    private final BlockingQueue<T> queue;
    private final int queueSize;

    public QueueBuffer(int size) {
        this.queueSize = size;
        this.queue = new ArrayBlockingQueue<>(size);
    }

    public void put(List<T> elementList) throws InterruptedException {
        if (2 * elementList.size() > this.queueSize)
            throw new RuntimeException("element list too big");
        for (T element : elementList)
            this.queue.put(element);
    }

    public List<T> take(int elementCount) throws InterruptedException {
        if (2 * elementCount > this.queueSize)
            throw new RuntimeException("element list too big");
        List<T> elementList = new LinkedList<>();
        for (int i = 0; i < elementCount; i++)
            elementList.add(this.queue.take());
        return elementList;
    }
}
