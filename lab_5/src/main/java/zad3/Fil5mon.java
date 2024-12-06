package zad3;


public class Fil5mon {

    public static void main(String[] args) {
        int iterationsNumber = 10_000_000;
        Butler butler =  new Butler();
        Fork[] forks = new Fork[5];
        for (int i = 0; i < 5; i++) {
            forks[i] = new Fork();
        }


        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(butler, iterationsNumber);
            philosophers[i].setLeftFork(forks[i]);
            philosophers[i].setRightFork(forks[(i+1)%5]);


        }

        for (int i = 0; i < 5; i++) {
            philosophers[i].start();
        }

        for(int i = 0; i < 5; i++){
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
