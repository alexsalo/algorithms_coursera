package structures;
import java.util.NoSuchElementException;

public class MaxPQ<Key extends Comparable<Key>> {
    private static final int INIT_CAPACITY = 2;
    private Key[] pq; // numbering from 1
    private int N;
    
    public MaxPQ(){
        this(INIT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
    private MaxPQ(int capacity){
        pq = (Key[]) new Comparable[capacity + 1];
    }
    
    @SuppressWarnings("unchecked")
    private void resize(int capacity){
        assert capacity > N;
        Key[] tmp = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= N; i++){
            tmp[i] = pq[i];
        }
        pq = tmp;
    }

    // ~lgN
    public Key delMax() {
        if (N == 0) throw new NoSuchElementException();
        Key max = pq[1]; // save max
        exch(1, N--); // put last key on top
        sink(1); // sink it to where it belongs
        pq[N + 1] = null; // prevent loitering
        if ((N > 0) && (N == (pq.length - 1) / 4))
            resize(pq.length / 2);
        assert isMaxHeap();
        return max;
    }

    public void insert(Key x) {
        if (N >= pq.length - 1)
            resize(2 * pq.length);
        pq[++N] = x;
        swim(N);
        assert isMaxHeap();
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public Key max() {
        return pq[1];
    }

    public int size() {
        return N;
    }
    
    // Helpers to restore heap invariants    
    // ~lgN
    private void sink(int k) {
        while (2 * k <= N) {
            // left kid
            int j = 2 * k;

            // choose larger kid if right one exists
            if (j < N && less(j, j + 1))
                j++;

            // if larger kid is no bigger than father - done here
            if (!less(k, j))
                break;

            // otherwise - exch
            exch(k, j);
            k = j;
        }
    }

    private void swim(int k) {
        // while parent exists and it's smaller than the kid - exch
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }
    
    private boolean isMaxHeap(){
        return isMaxHeap(1);
    }
    
    private boolean isMaxHeap(int k){
        if (k > N) return true; // border case
        int left = 2 * k;
        int right = left + 1;
        if (left <= N && less(k, left)) return false;
        if (right <= N && less(k, right)) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }
    
    // Helpers to deal with the array
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }
    
    public String toString(){
        String s = "";
        int i = 1;
        int powerOfTwo = 2;
        while (i <= N){
            s += pq[i].toString() + " ";
            i++;
            if (i == powerOfTwo){
                s += '\n';
                powerOfTwo *= 2;
            }
        }
        return s;
    }

    public static void main(String[] args) {
        MaxPQ<String> pq = new MaxPQ<String>();
        pq.insert("alex");
        pq.insert("stacey");
        pq.insert("vasek");
        pq.insert("yung");
        pq.insert("honza");
        pq.insert("sarah");
        pq.insert("forrest");
        System.out.println(pq);
        System.out.println(pq.delMax());
        System.out.println();
        System.out.println(pq);
    }

}
