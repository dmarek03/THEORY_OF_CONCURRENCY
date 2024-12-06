public class Consumer extends Thread{
    private int ID;
    private Proxy proxy;


    public Consumer(int ID, Proxy proxy){
        this.ID=ID;
        this.proxy = proxy;
    }


    @Override
    public void run(){

        int i = 0;
        while(i < 10){
            CustomFuture consumed =  proxy.remove();

            while(!consumed.isReady()){

                System.out.println("Consumer " +this.ID+ " is waiting");

                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Consumer " +this.ID+ " has eaten: " + consumed.getObject());

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;
        }

    }
}
