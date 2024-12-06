package zad3;

public class Fork {
    private boolean isAvailable = true;


    public synchronized void take() {
        isAvailable = false;

    }
    public synchronized void putDown() {
        isAvailable = true;

    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
