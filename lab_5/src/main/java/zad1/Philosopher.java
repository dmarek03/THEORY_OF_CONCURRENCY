package zad1;

import java.util.Random;

public class Philosopher extends Thread {
    private int id;
    private Fork leftFork;
    private Fork rightFork;

    private int counter = 0;
    private long startTime;
    private long endTime;




    public void run() {
        startTime = System.currentTimeMillis();
        while (true) {
            this.think();

            this.takeLeftFork();
            this.takeRightFork();

            // jedzenie
            this.eat();
            ++counter;
            System.out.println(counter);
            if (counter % 100 == 0) {
                endTime = System.currentTimeMillis();
                long deltaTime = endTime-startTime;
                System.out.println("Filozof: " + Thread.currentThread() +
                        "jadlem " + counter + " razy" + " czas" +deltaTime);
            }
            // koniec jedzenia


            this.putDownLeftFork();
            this.putDownRightFork();


            checkIfNotDead();

        }
    }

    private void checkIfNotDead() {
        if(endTime - startTime > 2000)
            System.out.println("Filozof: " + Thread.currentThread() + " nie zyje.");
    }


    private void putDownLeftFork(){
        System.out.println("Filozof: " + Thread.currentThread() + " odlozyl lewy.");
        this.leftFork.putDown();
    }

    private void putDownRightFork(){
        System.out.println("Filozof: " + Thread.currentThread() + " odlozyl prawy.");
        this.rightFork.putDown();
    }

    private void takeRightFork(){
        synchronized (this) {
            while (!rightFork.isAvailable()) {
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Filozof: " + Thread.currentThread() + " podniosl prawy: " + rightFork.getID());
        rightFork.take();
    }

    private void takeLeftFork(){
        synchronized (this) {
            while (!leftFork.isAvailable()) {
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Filozof: " + Thread.currentThread() + " podniosl lewy: " + leftFork.getID());
        leftFork.take();
    }

    public void think(){
        Random rand = new Random();
        int timeToThink = rand.nextInt(100) + 1;
        System.out.println("Filozof: " + Thread.currentThread() + " mysli.");
        try {
            sleep(timeToThink);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void eat(){
        Random rand = new Random();
        int timeToThink = rand.nextInt(100) + 1;
        System.out.println("Filozof: " + Thread.currentThread() + " je.");
        try {
            sleep(timeToThink);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void setRightFork(Fork rightFork) {
        this.rightFork = rightFork;
    }

    public void setLeftFork(Fork leftFork){
        this.leftFork=  leftFork;
    }
}

