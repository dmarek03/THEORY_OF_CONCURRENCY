import static java.lang.Thread.sleep;

class Buffer  implements IBuffer{
    private boolean is_available = false;
    private int data;

    @Override
    public  synchronized void put(int i,int producerId) {
        while (is_available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        this.data = i;
        is_available = true;

        notifyAll();
        System.out.println("Producer number " + producerId + " produce " + this.data);

    }

    @Override
    public synchronized int get(int consumerId) {
        while (!is_available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }


        is_available = false;
        notifyAll();
        System.out.println("Consumer number " + consumerId + " consume " + this.data);
        return this.data;
    }
}

