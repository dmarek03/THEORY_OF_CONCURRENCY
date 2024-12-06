package zad1;

public class Fork {

    private int id;
    private boolean isAvailable = true;

    public Fork(int id) {
        this.id = id;
    }


    public  synchronized void take(){
        isAvailable = false;
    }

    public  synchronized void putDown(){
        isAvailable =  true;


    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getID(){
        return id;
    }
}
