package zad2;



class Fork {
    private boolean isAvailable = true;


    public synchronized void take() {
        while(!isAvailable){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isAvailable = false;

    }
    public synchronized void putDown() {
        isAvailable = true;
        notifyAll();

    }

    public boolean isAvailable() {
        return isAvailable;
    }
}