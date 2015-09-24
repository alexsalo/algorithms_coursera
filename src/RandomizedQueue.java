import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 1;

    private int N;
    private int dataPos;
    private Item[] data;

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        data = (Item[]) new Object[INIT_CAPACITY];
        dataPos = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) { // ~
        if (item == null)
            throw new NullPointerException();
        if (dataPos == data.length) {
            resize(2 * data.length);
        }
        data[dataPos++] = item;
        N++;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) { // ~N
        Item[] newdata = (Item[]) new Object[capacity];
        int pos = 0;
        for (int i = 0; i < data.length; i++)
            if (data[i] != null)
                newdata[pos++] = data[i];
        data = newdata;
        dataPos = pos;
    }

    private void checkEmpty() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    public Item sample() {
        checkEmpty();
        int r;
        do {
            r = StdRandom.uniform(dataPos);
        } while (data[r] == null);
        return data[r];
    }

    public Item dequeue() {
        checkEmpty();
        int r;
        do {
            r = StdRandom.uniform(dataPos);
        } while (data[r] == null);
        Item item = data[r];
        data[r] = null;
        N--;
        if (N <= 0.25 * data.length) {
            if (N != 0) {
                resize(data.length / 2);
            }
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private int cnt;
        private Item[] randomdata;

        @SuppressWarnings("unchecked")
        public RandomArrayIterator() {
            cnt = 0;

            randomdata = (Item[]) new Object[N];
            int pos = 0;
            for (int i = 0; i < dataPos; i++)
                if (data[i] != null)
                    randomdata[pos++] = data[i];

            StdRandom.shuffle(randomdata);
        }

        @Override
        public boolean hasNext() {
            return cnt != N;
        }

        @Override
        public Item next() {
            if (cnt == N)
                throw new NoSuchElementException();
            // return randomdata[randomindex[cnt++]];
            return randomdata[cnt++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("alex");
        rq.dequeue();
        rq.enqueue("salo");

        for (Object s : rq)
            System.out.println(s);

        System.out.println(rq.N);
    }
}
