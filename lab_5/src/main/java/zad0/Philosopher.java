package zad0;

public class Philosopher extends Thread {
    private int counter = 0;
    private Fork leftFork;
    private Fork rightFork;

    public void run() {
        while (true) {

            // jedzenie

            leftFork.take();
            rightFork.take();

            ++counter;

            rightFork.putDown();
            leftFork.putDown();

            try {
                Thread.sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (counter % 100 == 0) {
                System.out.println("Filozof: " + Thread.currentThread() +
                        "jadlem " + counter + " razy");
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

