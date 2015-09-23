import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;

public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k > 0) {
            RandomizedQueue<String> rq = new RandomizedQueue<String>();
            for (int i = 0; i < k; i++) {
                rq.enqueue(StdIn.readString());
            }
            while (!StdIn.isEmpty()) {
                rq.dequeue();
                rq.enqueue(StdIn.readString());
            }
            Iterator<String> iter = rq.iterator();
            for (int i = 0; i < k; i++)
                System.out.println(iter.next());
        }
    }

}
