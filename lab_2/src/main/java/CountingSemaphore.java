public class CountingSemaphore {
    int resourcesCount;
    final BinarySemaphore mutex = new BinarySemaphore(true) ;
    final BinarySemaphore lock = new BinarySemaphore(false) ;


    public CountingSemaphore(int initialValue) {
        this.resourcesCount = initialValue;

    }

    public void P(){
        this.mutex.P();
        if(this.resourcesCount == 0){
            this.mutex.V();
            this.lock.P();
        }else{
            this.resourcesCount--;
            this.mutex.V();
        }


    }

    public void V(){
        this.mutex.P();
        this.resourcesCount++;
        if(this.resourcesCount == 1){
            this.lock.V();
        }
        this.mutex.V();



    }

}
