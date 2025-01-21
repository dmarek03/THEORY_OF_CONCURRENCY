package zad3;

import java.util.concurrent.Semaphore;
public class Buffer {
    private int items[];
    private Semaphore freePlaces;
    private Semaphore storedItems;
    private int firstFreePlace;
    private int firstStoredItem;
    public Buffer (int nItems)
    {
        items = new int[nItems];
        freePlaces = new Semaphore(nItems);
        storedItems = new Semaphore(0);
    }
    public void put(int item)
    {
        try {
            freePlaces.acquire();
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        items[firstFreePlace] = item;
        firstFreePlace = (firstFreePlace + 1) % items.length;
        storedItems.release();
    }
    public int get ()
    {
        try {
            storedItems.acquire();
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        int item = items[firstStoredItem];
        firstStoredItem = (firstStoredItem + 1) % items.length;
        freePlaces.release();
        return item;
    }
}
