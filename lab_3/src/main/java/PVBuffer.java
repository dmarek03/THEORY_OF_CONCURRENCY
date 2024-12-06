import java.util.concurrent.Semaphore;

public class PVBuffer implements IBuffer {

    private Semaphore empty = new Semaphore(1);
    private Semaphore full = new Semaphore(0);
    private int data;

    public PVBuffer() {}

    @Override
    public void put(int data, int producerId) {

        try {
            this.empty.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.data = data;
        this.full.release();
        System.out.println("Producer number " + producerId + " produce " + this.data);

    }
   @Override
    public int get(int consumerId) {
        try {
            this.full.acquire();
        } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
        }

        this.empty.release();
        System.out.println("Consumer number " + consumerId + " consume " + this.data);
        return this.data;
    }

}


