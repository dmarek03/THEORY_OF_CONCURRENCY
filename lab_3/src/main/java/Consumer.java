class Consumer extends Thread {
    private IBuffer _buf;
    private int id;


    public Consumer(IBuffer buf, int consumerId) {
        _buf = buf;
        this.id = consumerId;
    }


    public void run() {
        for (int i = 0; i < 100; ++i) {
            _buf.get(this.id);
//            try {
//                sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

        }
    }
}
