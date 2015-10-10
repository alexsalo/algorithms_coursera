package geometry;

import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class Point implements Comparable<Point> {
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private static final double NINFINITY = Double.NEGATIVE_INFINITY;

    private final int x; // x-coordinate of this point
    private final int y; // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static int ccw(Point a, Point b, Point c) {
        double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (area2 < 0)
            return -1; // cw
        if (area2 > 0)
            return 1; // ccw
        else
            return 0; // collinear
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (that == null)
            throw new NullPointerException();
        if (this.x == that.x)
            if (this.y == that.y)
                return NINFINITY;
            else
                return INFINITY;
        if (this.y == that.y)
            return +0.0;

        return (double) (that.y - this.y) / (that.x - this.x);
    }
    
    public double r(){
        return Math.sqrt(x * x + y * y);
    }
    
    public double theta(){
        return Math.atan2(y, x);
    }

    public double angleTo(Point that) {
        return Math.atan2((double) (that.y - y), (double) (that.x - x));
    }

    public int compareTo(Point that) {
        if (this.y < that.y)
            return -1;
        if (this.y > that.y)
            return 1;
        if (this.x < that.x)
            return -1;
        else if (this.y == that.y && this.x > that.x)
            return 1;
        else
            return 0;
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrderComparator();
    }

    private class SlopeOrderComparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            double slope1 = slopeTo(o1);
            double slope2 = slopeTo(o2);
            if      (slope1 < slope2) return -1;
            else if (slope1 > slope2) return 1;
            else                      return 0;
        }
    }

    public Comparator<Point> polarOrder() {
        return new PolarComparator();
    }

    private class PolarComparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            double angle1 = angleTo(o1);
            double angle2 = angleTo(o2);
            if (angle1 < angle2)  return -1;
            if (angle1 > angle2)  return 1;
            else                  return 0;
        }
    }

    public static Comparator<Point> YOrder() {
        return new YComparator();
    }

    private static class YComparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            if      (o1.y < o2.y) return -1;
            else if (o1.y > o2.y) return 1;
            else                  return 0;
        }
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        int x0 = 2; //Integer.parseInt(args[0]);
        int y0 = 2; //Integer.parseInt(args[1]);
        int N = 100; //Integer.parseInt(args[2]);

        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(.005);
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            points[i] = new Point(x, y);
            points[i].draw();
        }

        // draw p = (x0, x1) in red
        Point p = new Point(x0, y0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(.02);
        p.draw();


        // draw line segments from p to each point, one at a time, in polar order
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        Arrays.sort(points, p.polarOrder());
        for (int i = 0; i < N; i++) {
            p.drawTo(points[i]);
            StdDraw.show(100);
        }
    }
}