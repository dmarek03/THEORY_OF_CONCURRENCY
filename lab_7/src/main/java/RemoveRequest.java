public class RemoveRequest implements IMethodRequest{

    private Buffer buffer;
    private CustomFuture customFuture;


    public RemoveRequest(Buffer buffer, CustomFuture customFuture) {
        this.buffer = buffer;
        this.customFuture = customFuture;
    }


    @Override
    public void call(){
        customFuture.setObject(buffer.remove());
    }


    @Override
    public boolean guard(){
        return !this.buffer.isEmpty();
    }


}
