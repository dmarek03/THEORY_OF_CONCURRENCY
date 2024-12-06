package zad3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {
    Object value;
    Node next;
    Lock lock;

    public Node(Object value) {
        this.value = value;
        this.lock = new ReentrantLock();
        this.next = null;
    }
}

