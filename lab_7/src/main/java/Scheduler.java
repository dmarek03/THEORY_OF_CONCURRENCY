import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler extends Thread {

    private Queue<IMethodRequest> activationQueue;


    public Scheduler() {
        activationQueue = new ConcurrentLinkedQueue<IMethodRequest>();
    }


    public void enqueue(IMethodRequest request) {
        activationQueue.add(request);
    }



    public void run() {
        while(true){
            IMethodRequest request = activationQueue.poll();
            if(request != null){
                if(request.guard()){
                    request.call();
                }else {
                    activationQueue.add(request);
                }
            }
        }
    }



}
