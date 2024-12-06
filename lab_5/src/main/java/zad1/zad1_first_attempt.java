package zad1;

public class zad1_first_attempt {
    private static final int N = 5;
    public static void main(String[] args) {
        Philosopher[] filozofs = new Philosopher[N];
        Fork[] widelce = new Fork[N];

        for(int i=0; i<N; i++) {
            filozofs[i] = new Philosopher();
            widelce[i] = new Fork(i);
        }

        //przypisanie widelcow
        for(int i=0; i<N; i++) {
            filozofs[i].setLeftFork(widelce[i]);
        }

        for(int i=0; i<N; i++) {
            if(i==0) {
                filozofs[i].setRightFork(widelce[4]);
            } else {
                filozofs[i].setRightFork(widelce[i - 1]);
            }
        }

        //uruchomienie watkow z filozofami
        for(Philosopher f : filozofs) {
            f.start();
        }

        for(Philosopher f : filozofs) {
            try {
                f.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
