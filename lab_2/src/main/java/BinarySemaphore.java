//class BinarySemaphore {
//    private boolean state = true;
//    private int waitCounter = 0;
//
//    public BinarySemaphore() {
//    }
//
////    public synchronized void P() {
////        if(this.state){
////
////            this.state = false;
////
////        }else{
////            this.waitCounter++;
////            try {
////                this.wait();
////            } catch (InterruptedException e) {
////                throw new RuntimeException(e);
////            }
////        }
////
////    }
//    public synchronized void P() {
//        this.waitCounter++;
//        while (!this.state) {
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        this.state = false;
//    }
//
//    public synchronized void V() {
//        if(this.waitCounter > 0){
//
//            this.waitCounter--;
//            this.notify();
//        }
//        this.state = true;
//
//
//    }
//}


class BinarySemaphore {
    private boolean semaphoreState;




    public BinarySemaphore(boolean state) {
        this.semaphoreState = state;
    }

    public synchronized void P() {
        if(!semaphoreState) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        semaphoreState = false;
    }

    public synchronized void V() {
        semaphoreState = true;
        notify();
    }
}
