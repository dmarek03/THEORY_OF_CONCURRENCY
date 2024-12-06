package zad3;



public class Philosopher extends Thread {
    private int counter = 0;
    private Fork leftFork;
    private Fork rightFork;
    private Butler butler;
    private int iterations_number;


    public Philosopher(Butler butler, int iterations_number) {
        this.butler = butler;
        this.iterations_number = iterations_number;
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        while (true) {

            // jedzenie

            this.butler.passForks(this.leftFork, this.rightFork);

            ++counter;

            this.butler.takeForks(this.leftFork, this.rightFork);

            if (counter == iterations_number) {
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
