import java.util.Arrays;
import java.util.Stack;

import collinearPoints.Point;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

/*
 * Convex Hull - is the smallest perimeter fence that encloses the set of N points
 * 
 * 1. Points that the farthers apart are on the CH
 * 2. Shortest path through obstacle on the CH
 * 3. Point with smallest Y has angulars ordered
 */
public class ConvexHull {
    Stack<Point> hull;

    public ConvexHull(Point[] points) {
        hull = grahamScan(points);
    }

    public Point[] hull() {
        Point[] pp = new Point[hull.size()];
        pp = hull.toArray(pp);
        return pp;
    }

    private Stack<Point> grahamScan(Point[] points) {
        Stack<Point> hull = new Stack<>();
        // choose the lowest point to begin
        Arrays.sort(points, Point.YOrder());
        
        // sort by polar theta
        Arrays.sort(points, points[0].polarOrder());

        /*for (int i = 0; i < points.length; i++)
            System.out.println(points[0].angleTo(points[i]));*/

        // first to points are leading ccw for sure
        hull.push(points[0]);
        hull.push(points[1]);

        for (int i = 2; i < points.length; i++) {
            Point top = hull.pop();
            // while cw or collinear - unroll usless points
            while (Point.ccw(hull.peek(), top, points[i]) <= 0)
                top = hull.pop();

            // add updated ccw points
            hull.push(top);
            hull.push(points[i]);
        }
        return hull;
    }

    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        ConvexHull ch = new ConvexHull(points);
        Point[] pp = ch.hull();
        for (int i = 1; i < pp.length; i++)
            pp[i - 1].drawTo(pp[i]);
        pp[0].drawTo(pp[pp.length - 1]);
    }

}
