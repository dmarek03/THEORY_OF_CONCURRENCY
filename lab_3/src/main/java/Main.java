public class Main {

    public static void main(String[] args) {
        PVBuffer buf = new PVBuffer();
        int numberOfConsumer = 5;
        int numberOfProducers = 5;


        for (int i = 0; i < numberOfProducers; i++) {
            Producer producer = new Producer(buf, i);
            producer.start();
        }

        for (int i = 0; i < numberOfConsumer; i++) {
            Consumer consumer = new Consumer(buf, i);
            consumer.start();
        }

    }

}
