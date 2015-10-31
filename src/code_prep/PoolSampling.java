package code_prep;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

public class PoolSampling {
    private static class Node {
        int val;
        Node next;               
    }
    
    private static class Elem implements Comparable<Elem> {
        int val;
        double prob;
        public Elem(int val, double prob) {
            this.val = val;
            this.prob = prob;
        }
        @Override
        public int compareTo(Elem e) {
            if (this.prob < e.prob) return -1;
            if (this.prob > e.prob) return 1;
            return 0;
        }
        public String toString() {
            return String.format("%d (prob: %.2f)", this.val, this.prob);
        }
    }

    public static PriorityQueue<Elem> chooseKRandomElems(int k, Node list) {
        PriorityQueue<Elem> pq = new PriorityQueue<>(11, Collections.reverseOrder());
        Random r = new Random();
        Node x = list;
        while (x != null) {
            Elem e = new Elem(x.val, r.nextDouble());
            pq.add(e);
            x = x.next;
            if (pq.size() > k) // keep pq size = k
                pq.poll();
        }        
        return pq;
    }
    
    public static void print(Node x) {
        while (x != null) {
            System.out.print(x.val + " -> ");
            x = x.next;
        }
        System.out.println();
   }
    
    public static void main(String[] args) {
        Node list = new Node();
        for (int i = 0; i < 10; i++) {
            Node newlist = new Node();
            newlist.val = i;
            newlist.next = list;
            list = newlist;
        }
        Node x = list;
        print(x);
        
        System.out.println(chooseKRandomElems(3, list));
    }
}
