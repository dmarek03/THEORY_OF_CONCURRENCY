class Producer extends Thread {
    private IBuffer _buf;
    private int id;

    public Producer(IBuffer buf, int producerId) {
        this._buf = buf;
        this.id = producerId;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            _buf.put(i, this.id);
//            try {
//                sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

        }
    }
}
