package code_prep;

/*
 * Write a function to find the middle node of a single link list.
 */
public class MiddleOfSinglyLinkedList {
    private static class Node{
        Object val;
        Node next;
    }
    public static void printList(Node list) {
        Node x = list;
        while (true) {
            System.out.print(x.val);
            if (x.next != null) {
                System.out.print(" -> ");
                x = x.next;
            } else {
                System.out.println();
                break;
            }
        }
    }
    
    // go double fast with another pointer
    public static Object findMiddleElem(Node list) {
        Node x = list;
        Node xJumper = list;
        while (true) {
            if (xJumper.next != null && xJumper.next.next != null) {
                xJumper = xJumper.next.next;
                x = x.next;
            } else {
                return x.val;
            }
        }
    }
    
    public static void main(String[] args) {
        Node list = new Node();
        int N = 11;
        for (int i = 0; i < N; i++) {
            Node newnode = new Node();
            newnode.val = i;
            newnode.next = list;
            list = newnode;
        }
        
        printList(list);
        
        System.out.println("Middle is : " + findMiddleElem(list));
    }

}
