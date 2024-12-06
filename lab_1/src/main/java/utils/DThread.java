package utils;

// Watek, ktory dekrementuje licznik 100.000 razy
class DThread extends Thread {

    private Counter counter;

    public DThread (Counter counter) {

        this.counter = counter;
    }

    public void run(){
        for (int i  = 0 ; i < 10000 ; i ++){
            counter.dec();
        }

    }

}