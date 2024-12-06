// Race2.java
// wyscig

class Counter {
    private int _val;
    public Counter(int n) {
        _val = n;
    }
    public void inc() {
        _val++;
    }
    public void dec() {
        _val--;
    }
    public int value() {
        return _val;
    }
}

class IThread extends Thread {
    private Counter _cnt;
    private BinarySemaphore binSem;
    public IThread(Counter c, BinarySemaphore binSem) {
        this._cnt = c;
        this.binSem = binSem;
    }
    public void run() {
        for (int i = 0; i < 100_000; ++i) {
//		try { this.sleep(50); }
//			catch(Exception e) {}
            this.binSem.P();
            _cnt.inc();
            this.binSem.V();
        }
    }
}

class IThreadCnt extends Thread {
    private Counter _cnt;
    private CountingSemaphore cntSem;

    public IThreadCnt(Counter c, CountingSemaphore cntSem) {
        this._cnt = c;
        this.cntSem = cntSem;
    }
    public void run() {
        for (int i = 0; i < 100_000; ++i) {
//		try { this.sleep(50); }
//			catch(Exception e) {}
            this.cntSem.P();
            _cnt.inc();
            this.cntSem.V();
            System.out.println(i);
        }
    }
}


class DThread extends Thread {
    private Counter _cnt;
    private BinarySemaphore binSem;

    public DThread(Counter c, BinarySemaphore binSem) {
        this._cnt = c;
        this.binSem = binSem;
    }
    public void run() {
        for (int i = 0; i < 100_000; ++i) {
            this.binSem.P();
            _cnt.dec();
            this.binSem.V();
//		try { this.sleep(1); }
//			catch(Exception e) {}
        }
    }
}

class DThreadCnt extends Thread {
    private Counter _cnt;
    private CountingSemaphore cntSem;


    public DThreadCnt(Counter c, CountingSemaphore cntSem) {
        this._cnt = c;
        this.cntSem = cntSem;
    }
    public void run() {
        for (int i = 0; i < 100_000; ++i) {
            this.cntSem.P();
            _cnt.dec();
            this.cntSem.V();
            System.out.println(i);
//		try { this.sleep(1); }
//			catch(Exception e) {}
        }
    }
}


class Race2 {
    public static void main(String[] args) {
        Counter cnt = new Counter(0);
        BinarySemaphore binSem = new BinarySemaphore(true);

        IThread it = new IThread(cnt, binSem);
        DThread dt = new DThread(cnt, binSem);


        it.start();
        dt.start();

        try {
            it.join();
            dt.join();
        } catch(InterruptedException ie) { }

        System.out.println("value=" + cnt.value());
    }
}