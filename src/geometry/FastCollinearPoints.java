package geometry;
import java.util.ArrayList;
import java.util.Arrays;

// The problem. Given a set of N distinct points in the plane, find every
// (maximal) line segment that connects a subset of 4 or more of the points.
public class FastCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> segments;
    private ArrayList<LS> ls;

    /*
     * Private class to represent potential line segment uniquely by (slope, min
     * point)
     */
    private class LS implements Comparable<LS> {
        private Point max;
        private Point min;
        private double slope;

        public LS(Point max, Point min, double slope) {
            this.max = max;
            this.min = min;
            this.slope = slope;
        }

        @Override
        public int compareTo(LS o) {
            int comp = Double.compare(slope, o.slope);
            if (comp < 0)
                return -1;
            else if (comp > 0)
                return 1;
            else {
                comp = min.compareTo(o.min);
                if (comp == 0)
                    return 0;
                else if (comp < 0)
                    return -1;
                else
                    return 1;
            }
        }
    }

    // finds all line segments containing >=4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException();
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = points[i];
        }

        // make sure no duplicate points
        assertUniquness();

        // find segments
        findSegments();
    }

    private void assertUniquness() {
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++)
            if (points[i].compareTo(points[i - 1]) == 0)
                throw new IllegalArgumentException();
    }

    private void findSegments() {
        int N = points.length;
        ls = new ArrayList<FastCollinearPoints.LS>();

        // make a copy for iterator
        Point[] pp = new Point[N];
        for (int i = 0; i < N; i++)
            pp[i] = points[i];

        // for each point: sort other points by slope to current point and find
        // consequent points with same slope
        for (int i = 0; i < N; i++) {
            Point p = pp[i];
            Arrays.sort(points, p.slopeOrder());

            int pos = 1; // check with previous if same slope
            while (pos < N) {
                int n = 2; // 0 is the p itself
                double slopePos = p.slopeTo(points[pos - 1]);
                while (pos < N && slopePos == p.slopeTo(points[pos])) {
                    n++;
                    pos++;
                }
                if (n >= 4) { // if 3 or more consequent points
                    Point max = p;
                    Point min = p;
                    for (int j = 1; j < n; j++) {
                        if (points[pos - j].compareTo(max) > 0)
                            max = points[pos - j];
                        if (points[pos - j].compareTo(min) < 0)
                            min = points[pos - j];
                    }

                    ls.add(new LS(min, max, slopePos));
                }
                pos++;
            }
        }

        LS[] lsa = new LS[ls.size()];
        for (int i = 0; i < ls.size(); i++)
            lsa[i] = ls.get(i);

        Arrays.sort(lsa);

        // choose unique segments
        segments = new ArrayList<LineSegment>();
        for (int i = 0; i < lsa.length - 1; i++)
            if (lsa[i].compareTo(lsa[i + 1]) != 0 || i == lsa.length - 2)
                segments.add(new LineSegment(lsa[i].min, lsa[i].max));
    }

    public int numberOfSegments() // the number of line segments
    {
        return segments.size();
    }

    // return a copy of found segments
    public LineSegment[] segments() // the line segments
    {
        LineSegment[] seg = new LineSegment[segments.size()];
        for (int i = 0; i < seg.length; i++)
            seg[i] = segments.get(i);
        return seg;
    }
}
