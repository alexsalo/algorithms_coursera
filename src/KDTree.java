import java.util.ArrayList;

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
    private static final boolean VERTICAL = true;
    private Node root;

    private static class Node {
        private Point2D p;
        private Node left, right;

        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;
        private int count;

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.count = 1;
            this.rect = rect;
        }
    }

    // keeps the duplicates
    public void insert(Point2D p) {
        root = insert(root, p, VERTICAL, null, false);
    }

    private Node insert(Node h, Point2D p, boolean direction, Node parent,
            boolean isLeft) {
        if (h == null) {
            // root
            if (parent == null)
                return new Node(p, new RectHV(0, 0, 1, 1)); // unit square

            RectHV r = parent.rect;
            RectHV rect;
            double split;

            // direction flipped at the parent
            if (direction == VERTICAL)
                split = parent.p.y();
            else
                split = parent.p.x();

            // figure out kid rect by splitting parent's rect
            if (direction == VERTICAL)
                if (isLeft)
                    rect = new RectHV(r.xmin(), r.ymin(), r.xmax(), split);
                else
                    rect = new RectHV(r.xmin(), split, r.xmax(), r.ymax());
            else if (isLeft)
                rect = new RectHV(r.xmin(), r.ymin(), split, r.ymax());
            else
                rect = new RectHV(split, r.ymin(), r.xmax(), r.ymax());

            // System.out.println("creating node with p " + p + " rect: " +
            // rect);
            return new Node(p, rect);
        }

        boolean cmp;
        if (direction == VERTICAL)
            cmp = p.x() < h.p.x();
        else
            cmp = p.y() < h.p.y();

        // binary search the position
        if (cmp)
            h.left = insert(h.left, p, !direction, h, true);
        else
            h.right = insert(h.right, p, !direction, h, false);

        // maintain size
        h.count = 1 + size(h.left) + size(h.right);
        return h;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return root.count;
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        return x.count;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node h, Point2D p, boolean direction) {
        if (h == null)
            return false;

        if (h.p.equals(p))
            return true;

        boolean cmp;
        if (direction == VERTICAL)
            cmp = p.x() < h.p.x();
        else
            cmp = p.y() < h.p.y();

        // binary search the position
        if (cmp)
            return contains(h.left, p, !direction);
        else
            return contains(h.right, p, !direction);
    }

    public void draw() {
        draw(root, VERTICAL);
    }

    private void draw(Node h, boolean direction) {
        if (h != null) {
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            h.p.draw();

            StdDraw.setPenRadius(.001);
            RectHV r = h.rect;
            Point2D p = h.p;
            if (direction == VERTICAL) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(p.x(), r.ymin(), p.x(), r.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(r.xmin(), p.y(), r.xmax(), p.y());
            }

            draw(h.left, !direction);
            draw(h.right, !direction);
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();

        ArrayList<Point2D> points = new ArrayList<Point2D>();

        // check root
        if (rect.contains(root.p))
            points.add(root.p);

        // accumulate points
        range(root.left, rect, points);
        range(root.right, rect, points);
        return points;
    }

    private void range(Node h, RectHV rect, ArrayList<Point2D> points) {
        if (h != null)
            // if intersects - we may find point at the left and right
            if (rect.intersects(h.rect)) {
                range(h.left, rect, points);
                range(h.right, rect, points);

                if (rect.contains(h.p))
                    points.add(h.p);
            }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D closetPoint = root.p;

        if (root != null)
            closetPoint = minDist(root, p, closetPoint);

        return closetPoint;
    }

    private Point2D minDist(Node h, Point2D p, Point2D closetPoint) {
        if (p.distanceTo(closetPoint) > h.rect.distanceSquaredTo(p)) {
            if (p.distanceTo(h.p) < p.distanceTo(closetPoint))
                closetPoint = h.p;

            // check the side that contains the point first - more likely
            // point is in there
            if (h.left != null && h.left.rect.contains(closetPoint)) {
                closetPoint = minDist(h.left, p, closetPoint);
                if (h.right != null)
                    closetPoint = minDist(h.right, p, closetPoint);
            } else if (h.right != null) {
                closetPoint = minDist(h.right, p, closetPoint);
                if (h.left != null)
                    closetPoint = minDist(h.left, p, closetPoint);
            }

            return closetPoint;
        }
        return closetPoint;
    }

    /*
     * private Iterable<Point2D> keySet(){ Queue<Point2D> pp = new
     * LinkedList<Point2D>(); inOrderTraversal(root, pp); return pp; }
     * 
     * private Iterable<Point2D> keySet(Node h){ Queue<Point2D> pp = new
     * LinkedList<Point2D>(); inOrderTraversal(h, pp); return pp; }
     * 
     * private void inOrderTraversal(Node start, Queue<Point2D> keys){ if (start
     * == null) return; inOrderTraversal(start.left, keys); keys.add(start.p);
     * inOrderTraversal(start.right, keys); }
     */

    public static void main(String[] args) {
        StdDraw.setXscale();
        StdDraw.setYscale();
        KdTree d2tree = new KdTree();
        // String filename = "kd-tree-input.txt";
        String filename = "kdtree2.txt";
        // Scanner scanner = new Scanner(new BufferedReader(new
        // FileReader(filename)));
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            d2tree.insert(new Point2D(x, y));
        }

        // System.out.println(d2tree.get(new Point2D(0.37, 0.56)));
        System.out.println(d2tree.contains(new Point2D(0.81, 0.66)));
        System.out.println(d2tree.root.left.rect);

        d2tree.draw();

        RectHV rect = new RectHV(0.23, 0.4, 0.61, 0.86);
        StdDraw.setPenColor(StdDraw.BLUE);
        rect.draw();

        for (Point2D p : d2tree.range(rect))
            StdDraw.filledCircle(p.x(), p.y(), 0.01);

        Point2D point = new Point2D(0.33, 0.44);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(point.x(), point.y(), 0.01);

        Point2D pc = d2tree.nearest(point);
        StdDraw.setPenColor(StdDraw.GREEN);
        point.drawTo(pc);

        /*
         * RectHV rect = new RectHV(0.2, 0.46, 0.6, 0.7);
         * StdDraw.setPenColor(StdDraw.BLUE); rect.draw();
         * 
         * for (Point2D p : d2tree.range(rect)) StdDraw.filledCircle(p.x(),
         * p.y(), 0.01);
         */

    }
}
