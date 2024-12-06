package zad3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoarseGrainedLockingList {
    private Node head;
    private final Lock listLock;

    public CoarseGrainedLockingList() {
        head = new Node(null);
        listLock = new ReentrantLock();
    }

    // Metoda contains - sprawdza, czy lista zawiera dany element
    public boolean contains(Object o) {
        listLock.lock();
        try {
            Node current = head.next;
            while (current != null) {
                if (current.value.equals(o)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        } finally {
            listLock.unlock();
        }
    }

    // Metoda remove - usuwa pierwsze wystąpienie elementu
    public boolean remove(Object o) {
        listLock.lock();
        try {
            Node current = head;
            while (current.next != null) {
                Node nextNode = current.next;
                if (nextNode.value.equals(o)) {
                    current.next = nextNode.next;
                    return true;
                }
                current = nextNode;
            }
            return false;
        } finally {
            listLock.unlock();
        }
    }

    // Metoda add - dodaje element na końcu listy
    public void add(Object o) {
        listLock.lock();
        try {
            Node newNode = new Node(o);
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        } finally {
            listLock.unlock();
        }
    }
}
