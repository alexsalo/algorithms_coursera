import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head;
    private Node<Item> tail;
    private int N;

    private class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(E value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    public Deque() {
        this.N = 0;
    }

    private void checkItemNull(Item item) {
        if (item == null)
            throw new NullPointerException();
    }

    public void addFirst(Item item) {
        checkItemNull(item);
        Node<Item> oldHead = head;
        head = new Node<Item>(item);
        if (N == 0)
            tail = head;
        else {
            head.next = oldHead;
            oldHead.prev = head;
        }
        N++;
    }

    public void addLast(Item item) {
        checkItemNull(item);
        Node<Item> oldTail = tail;
        tail = new Node<Item>(item);
        if (N == 0)
            head = tail;
        else {
            if (oldTail != null) {
                oldTail.next = tail;
                tail.prev = oldTail;
            } else {
                head.next = tail;
                tail.prev = head;
            }
        }
        N++;
    }

    private void checkRemoveFromEmpty() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    private void makeEmpty() {
        head = null;
        tail = null;
    }

    public Item removeFirst() {
        checkRemoveFromEmpty();
        Item item = head.value;
        if (head.next != null) {
            head = head.next;
            head.prev = null;
        } else {
            makeEmpty();
        }
        N--;
        return item;
    }

    public Item removeLast() {
        checkRemoveFromEmpty();
        Item item = tail.value;
        if (tail.prev != null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            makeEmpty();
        }
        N--;
        return item;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> node = head;

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public Item next() {
            if (node == null)
                throw new NoSuchElementException();
            Item value = node.value;
            node = node.next;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        /*
         * d.addLast("I"); d.addLast("am"); d.addLast("Alex");
         * d.addLast("Salo");
         */

        d.addFirst("I");
        d.removeFirst();
        d.addFirst("I");
        d.removeLast();
        // d.isEmpty();
        // d.addFirst("Am");
        // d.removeLast();

        /*
         * d.addLast("Google"); d.addLast("Hey"); d.removeLast();
         * System.out.println(d.size());
         */
        /*
         * d.addFirst("like"); d.addFirst("Yoda");
         */

        for (String s : d)
            System.out.println(s);

        /*
         * System.out.println(); System.out.println(d.removeFirst());
         * System.out.println(d.removeFirst());
         * System.out.println(d.removeLast());
         * System.out.println(d.removeLast()); System.out.println(d.size());
         */
    }
}
