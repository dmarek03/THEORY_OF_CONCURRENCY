import java.util.Random;

public class Producer extends Thread{

    private int ID;
    private Proxy proxy;

    private Random rand;


    public Producer(int ID, Proxy proxy) {
        this.ID = ID;
        this.proxy = proxy;
        rand = new Random();
    }


    @Override
    public void run(){

        int i = 0;
        while(i < 10){
             int tmp = this.rand.nextInt(100);

             proxy.add(tmp);

            System.out.println("Producer " + ID + " added:  " + tmp);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;
        }
    }

}
