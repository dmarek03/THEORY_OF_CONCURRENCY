package zad3;

public class Butler {
    private int takenForkNumber = 0;

   

    public synchronized void passForks(Fork leftFork, Fork rightFork){
        while(!leftFork.isAvailable() || !rightFork.isAvailable()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(takenForkNumber == 4){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        takenForkNumber += 2;
        leftFork.take();
        rightFork.take();
    }

    public synchronized void takeForks(Fork leftFork, Fork rightFork){
        takenForkNumber -= 2;
        leftFork.putDown();
        rightFork.putDown();
        notifyAll();
    }


}
