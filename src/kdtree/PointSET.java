package kdtree;
import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.size() == 0;
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        if (!points.contains(p))
            points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points)
            // p.draw();
            StdDraw.filledCircle(p.x(), p.y(), 0.01);
    }

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
        if (p == null)
            throw new NullPointerException();

        if (!points.isEmpty()) {
            Point2D closetPoint = points.first();
            double minDist = 1;
            for (Point2D pp : points) {
                double dist = p.distanceSquaredTo(pp);
                if (dist < minDist) {
                    minDist = dist;
                    closetPoint = pp;
                }
            }
            return closetPoint;
        } else
            return null;
    }

    public static void main(String[] args) {
        StdDraw.setXscale();
        StdDraw.setYscale();
        PointSET pset = new PointSET();
        String filename = "kd-tree-input.txt";
        // Scanner scanner = new Scanner(new BufferedReader(new
        // FileReader(filename)));
        In in = new In(filename);
        int N = in.readInt();
        for (int i = 0; i < N; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            pset.insert(new Point2D(x, y));
        }

        pset.draw();

        Point2D point = new Point2D(0.3, 0.4);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(point.x(), point.y(), 0.01);

        Point2D pc = pset.nearest(point);
        StdDraw.setPenColor(StdDraw.GREEN);
        point.drawTo(pc);

        RectHV rect = new RectHV(0.2, 0.46, 0.6, 0.7);
        StdDraw.setPenColor(StdDraw.BLUE);
        rect.draw();

        for (Point2D p : pset.range(rect))
            StdDraw.filledCircle(p.x(), p.y(), 0.01);

    }

}
