import java.util.ArrayList;
import java.util.Queue;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


/*
 * Insert: alternate y and x coordinates as a key for inserting into BST
 * 
 * Search: if rect to the left - search left, if rect intersects - search both
 */
public class KdTree {    
    //private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;
    
    private Node root;    

/*    // construct an empty set of points
    public KdTree() {
    }*/
    
    private class Node {
        private Point2D p;
        private Node left, right;
        private int count;

        public Node(Point2D p) {
            this.p = p;
            this.count = 1;
        }
    }
    /****************************************************************
     ************************* PUT GET DELETE ***********************
     ****************************************************************/    
    
    // Overrides existing value on this key
    public void insert(Point2D p) {
        root = put(root, p, VERTICAL);
    }
    
    private Node put(Node h, Point2D p, boolean direction){
        if (h == null) return new Node(p);
        
        direction = !direction;    
        
        boolean cmp;
        if (VERTICAL)
            cmp = p.x() < h.p.x();
        else
            cmp = p.y() < h.p.y();
        
        // binary search the position
        if   (cmp) h.left  = put(h.left,  p, direction);
        else       h.right = put(h.right, p, direction);
        
        // maintain size
        h.count = 1 + size(h.left) + size(h.right);
        return h;
    }

    // return null if not present
    // cost: 1 + depth of the tree
    private Point2D get(Point2D p) {
        Node x = root;
        boolean direction = VERTICAL;
        while (x != null){
            double key;
            double nodekey;
            if (VERTICAL){ // vertical - compare x-coords
                key = p.x();
                nodekey = x.p.x();
            }
            else{ // horizontal - compare y-coords
                key = p.y();
                nodekey = x.p.y();
            }            
            
            if       (key < nodekey) x = x.left;
            else if (key > nodekey)     x = x.right;
            
            // comp coords equal - maybe found candidate
            else
                if (p.equals(x.p))
                    return x.p;
                else
                    return null;
            
            direction = !direction;
        }
        return null; // not found
    }

    
    /****************************************************************
     ******************** K-d Tree API ******************************
     ****************************************************************/  

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return root.count;
    }
    
    private int size(Node x){
        if (x == null)  return 0;
        return x.count;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return get(p) == null;
    }
      
    private void drawKdTree(Node start, boolean direction, boolean isLeft, Node parent, Node grandparent,
            Node ggp){
        if (start == null) return;
        
        StdDraw.setPenRadius(.01);
        start.p.draw();
        StdDraw.setPenRadius(.001);
        
        double lo;
        double hi;
        
        // root
        if (parent == null){
            lo = 0;
            hi = 1;
        }
        
        // second level
        else if (grandparent == null){
            if (isLeft){
                lo = 0;
                hi = parent.p.y();
            } else{
                lo = parent.p.y(); 
                hi = 1;
            }
        }
        
        else if (ggp == null){
            if (isLeft){
                lo = 0;
                hi = parent.key;
            } else{
                lo = parent.key; 
                hi = 1;
            }
        } else{
            if (isLeft){
                lo = ggp.key;
                hi = parent.key;
            } else{
                lo = parent.key;
                hi = ggp.key;
            }
        }
            
        if (direction == VERTICAL){
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(start.p.x(), lo, start.p.x(), hi);
        } else{
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(lo, start.p.y(), hi, start.p.y());
        }
        
        direction = !direction;        
        drawKdTree(start.left, direction, true, start, parent, grandparent);
        drawKdTree(start.right, direction, false, start, parent, grandparent);        
    }
    
    // draw all points to standard draw
    public void draw() {
        drawKdTree(root, VERTICAL, true, null, null, null);
    }
    /*    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        ArrayList<Point2D> pp = new ArrayList<Point2D>();
        for (Point2D p : points)
            if (rect.distanceTo(p) == 0)
                pp.add(p);
        return pp;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D closetPoint = points.first();
        double minDist = 1;
        for (Point2D pp : points) {
            double dist = p.distanceTo(pp);
            if (dist < minDist) {
                minDist = dist;
                closetPoint = pp;
            }
        }
        return closetPoint;
    }*/
    
    public static void main(String[] args) {
        StdDraw.setXscale();
        StdDraw.setYscale();
        KdTree d2tree = new KdTree();
        //String filename = "kd-tree-input.txt";
        String filename = "kdtree2.txt";
        // Scanner scanner = new Scanner(new BufferedReader(new
        // FileReader(filename)));
        In in = new In(filename);
        int N = in.readInt();
        for (int i = 0; i < N; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            d2tree.insert(new Point2D(x, y));
        }

        //System.out.println(d2tree.get(new Point2D(0.37, 0.56)));
        d2tree.draw();

        /*Point2D point = new Point2D(0.3, 0.4);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(point.x(), point.y(), 0.01);

        Point2D pc = d2tree.nearest(point);
        StdDraw.setPenColor(StdDraw.GREEN);
        point.drawTo(pc);

        RectHV rect = new RectHV(0.2, 0.46, 0.6, 0.7);
        StdDraw.setPenColor(StdDraw.BLUE);
        rect.draw();

        for (Point2D p : d2tree.range(rect))
            StdDraw.filledCircle(p.x(), p.y(), 0.01);*/

    }
}
