public class PipelineBuffer {
    private static class Buffer {
        private int[] productionLine;

        public Buffer(int size) {
            this.productionLine = new int[size];
            for (int i = 0; i < size; i++) {
                productionLine[i] = -1;
            }
        }


        public synchronized void startProcess(int cell, int processorId) throws InterruptedException {
            while (productionLine[cell] != processorId - 1) {
                wait();
            }
            System.out.println("I got the cell " + cell + " process " + processorId);
        }

        public synchronized void endProcess(int cell, int processorId) {
            productionLine[cell] = processorId;
            notifyAll();  
        }

        public int getSize() {
            return productionLine.length;
        }
    }

    private static class Consumer extends Processor {

        public Consumer(Buffer buffer, int id) {
            super(buffer, id);
        }

        @Override
        public void method(int i) {
            super.method(i);
            System.out.println("Consumer have eaten cell nr " + i);
        }

    }

    private static class Producent extends Processor {

        public Producent(Buffer buffer, int id) {
            super(buffer, id);
        }

        public void method(int i) {
            super.method(i);
            System.out.println("Producent touched cell nr " + i);
        }
    }

    private static class Processor implements Runnable {
        private Buffer buffer;
        private int id;

        public Processor(Buffer buffer, int id) {
            this.buffer = buffer;
            this.id = id;
        }

        public void run() {
            for (int i = 0; i < buffer.getSize(); i++) {
                method(i);
            }
        }

        public void method(int i) {
            try {
                buffer.startProcess(i, id);
                Thread.sleep((int) Math.floor(Math.random() * 1000));
                buffer.endProcess(i, id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Buffer buffer = new Buffer(100);  // Bufor o rozmiarze 100
        Thread producent = new Thread(new Producent(buffer, 0));  // Producent
        Thread[] processors = new Thread[5];  // 5 Procesorów
        for (int i = 1; i <= 5; i++) {
            processors[i - 1] = new Thread(new Processor(buffer, i));
        }
        Thread consumer = new Thread(new Consumer(buffer, 6));  // Konsument

        // Uruchamianie wątków
        producent.start();
        for (int i = 1; i <= 5; i++) {
            processors[i - 1].start();
        }
        consumer.start();
    }
}
