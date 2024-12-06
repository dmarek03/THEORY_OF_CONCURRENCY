public class ActiveObject {

    private Buffer buffer;
    private Scheduler scheduler;
    private Proxy proxy;


    public ActiveObject(int queueSize) {
        this.buffer =  new Buffer(queueSize);
        this.scheduler = new Scheduler();
        this.proxy = new Servant(this.buffer, this.scheduler);
        scheduler.start();
    }


    public Proxy getProxy(){
        return proxy;
    }

}
