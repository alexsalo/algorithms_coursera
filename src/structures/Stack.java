package structures;

import java.util.Iterator;

public class Stack<T> implements Iterable<T> {
    private Node<T> root;
    private int N;
    
    private static class Node<T> {
        private T val;
        private Node<T> next;
    }
    
    public Stack(){
        root = null;
        N = 0;
    }
    
    public void push(T val) { 
        Node<T> old = root;
        root = new Node<T>();
        root.val = val;
        root.next = old;
        N++;
    }
    
    public T pop() {
        Node<T> x = root;
        root = root.next;
        N--;
        return x.val;
    }
    
    public String toString() {
        String s = "";
        Node<T> x = root;
        while (x != null) {
            s += x.val;
            x = x.next;
            if (x != null)
                s += "; ";
        }
        return s;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> x = root;
            
            @Override
            public boolean hasNext() {
                return x != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T val = x.val;
                    x = x.next;
                    return val;
                }
                return null;
            }
        };
    }

    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();
        stack.push("alpha");
        stack.push("beta");
        System.out.println(stack);
        /*System.out.println(stack.pop());
        System.out.println(stack.pop());*/
        for (String s : stack)
            System.out.println(s);
    }

}
