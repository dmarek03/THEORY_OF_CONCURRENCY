public class Servant  implements Proxy{

    Buffer buffer;
    Scheduler scheduler;

    public Servant(Buffer buffer, Scheduler scheduler) {
        this.buffer = buffer;
        this.scheduler = scheduler;
    }


    @Override
    public void add(Object o){
        this.scheduler.enqueue(new AddRequest(this.buffer, o));

    }

    @Override
    public CustomFuture remove(){
        CustomFuture customFuture = new CustomFuture();
        this.scheduler.enqueue(new RemoveRequest(this.buffer, customFuture));
    return customFuture;
    }


}
