package zad2;


public class Philosopher extends Thread {
    private int counter = 0;
    private Fork leftFork;
    private Fork rightFork;
    private final Object lock;
    private final int iterationsNumber;


    public Philosopher(Object objectLock, int iterationsNumber) {
        this.lock = objectLock;
        this.iterationsNumber = iterationsNumber;
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        while (true) {

            // jedzenie

            synchronized (this.lock) {
                leftFork.take();
                rightFork.take();
            }

            ++counter;

            rightFork.putDown();
            leftFork.putDown();
            if (counter == iterationsNumber) {
                long endTime  = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                System.out.println("Filozof: " + Thread.currentThread() + "jadlem " + counter + " razy" + " czas:" +elapsedTime);
                break;

            }



        }
    }


    public void setLeftFork(Fork leftFork) {
        this.leftFork = leftFork;
    }

    public void setRightFork(Fork rightFork) {
        this.rightFork = rightFork;
    }


}