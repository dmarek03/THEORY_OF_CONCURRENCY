package utils;

import java.util.concurrent.atomic.AtomicBoolean;

public class PredictableCounter extends Counter {

    private int _val;
    final AtomicBoolean isDecrementing = new AtomicBoolean(false);
    public PredictableCounter(int n) {
        super(n);
    }
    @Override
    public void inc() {
        while (isDecrementing.get()) {}
        _val++;
        isDecrementing.set(true);

    }

    @Override
    public void dec() {
        while (!isDecrementing.get()) {}
        _val--;
        isDecrementing.set(false);

    }
}
