public class AddRequest implements IMethodRequest {


    private final Buffer buffer;
    private final Object object;


    public AddRequest(Buffer buffer, Object object) {
        this.buffer = buffer;
        this.object = object;
    }

    @Override
    public void call(){
        this.buffer.add(this.object);
    }


    @Override
    public boolean guard(){
        return !this.buffer.isEmpty();
    }

}
