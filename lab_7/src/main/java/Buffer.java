import java.util.LinkedList;
import java.util.Queue;

public class Buffer {


    private final int buffSiz;
    private Queue<Object> buffer;

    public Buffer(int buffSiz){
        this.buffSiz = buffSiz;
        this.buffer = new LinkedList<Object>();
    }


    public void add(Object o){
        if (!this.isFull()){
            this.buffer.add(o);
        }
    }


    public Object remove(){
        if(this.isEmpty()){
            return null;
        }
        return this.buffer.remove();
    }




    public  boolean isFull(){
        return buffer.size() == buffSiz;
    }

    public boolean isEmpty(){
        return buffer.isEmpty();
    }
}
