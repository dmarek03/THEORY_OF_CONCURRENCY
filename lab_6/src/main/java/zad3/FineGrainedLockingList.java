package zad3;

public class FineGrainedLockingList {
    private Node head;

    public FineGrainedLockingList() {
        head = new Node(null); // Głowa listy, pomocniczy węzeł.
    }

    // Metoda contains - sprawdza, czy lista zawiera dany element.
    public boolean contains(Object o) {
        Node current = head.next;

        while (current != null) {
            current.lock.lock(); // Zamek na bieżącym elemencie
            try {
                if (current.value.equals(o)) {
                    return true;
                }
            } finally {
                current.lock.unlock();
            }
            current = current.next;
        }
        return false;
    }

    // Metoda remove - usuwa pierwsze wystąpienie elementu
    public boolean remove(Object o) {
        Node current = head;
        Node prev = null;

        while (current.next != null) {
            Node nextNode = current.next;
            nextNode.lock.lock(); // Zamek na następniku

            try {
                if (nextNode.value.equals(o)) {
                    // Usuwanie węzła
                    if (prev != null) {
                        prev.next = nextNode.next;
                    }
                    return true;
                }
                prev = current;
                current = nextNode;
            } finally {
                nextNode.lock.unlock(); // Odblokowanie węzła
            }
        }
        return false;
    }

    // Metoda add - dodaje element na końcu listy
    public void add(Object o) {
        Node newNode = new Node(o);
        Node current = head;

        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }
}
