package zad3;

public class Producer extends Thread {
    private Buffer buff;
    private int howMany;
    public Producer (final Buffer buff, final int howMany)
    {
        this.buff = buff;
        this.howMany = howMany;
    }
    @Override
    public void run()
    {
        for (int i = 0; i < howMany; i++)
        {
            int item = (int)(Math.random()*100)+1;
            buff.put(item);
        }
    }
}