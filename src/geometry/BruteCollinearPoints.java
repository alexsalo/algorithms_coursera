package geometry;
import java.util.ArrayList;

//The problem. Given a set of N distinct points in the plane, find every
//(maximal) line segment that connects a subset of 4 or more of the points.
public class BruteCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException();
        this.points = new Point[points.length];
        int pos = 0;
        for (Point p : points) {
            if (p == null)
                throw new NullPointerException();
            if (unique(p, pos))
                this.points[pos++] = p;
            else
                throw new IllegalArgumentException();
        }

        segments = new ArrayList<LineSegment>();
        findSegments();
    }

    private void findSegments() {
        int N = points.length;
        for (int p = 0; p < N; p++)
            for (int q = p + 1; q < N; q++)
                for (int r = q + 1; r < N; r++)
                    for (int s = r + 1; s < N; s++) {
                        double pq = points[p].slopeTo(points[q]);
                        double pr = points[p].slopeTo(points[r]);
                        double ps = points[p].slopeTo(points[s]);
                        if (pq == pr && pq == ps) {
                            Point max = points[p];
                            if (points[q].compareTo(max) > 0)
                                max = points[q];
                            if (points[r].compareTo(max) > 0)
                                max = points[r];
                            if (points[s].compareTo(max) > 0)
                                max = points[s];

                            Point min = points[p];
                            if (points[q].compareTo(min) < 0)
                                min = points[q];
                            if (points[r].compareTo(min) < 0)
                                min = points[r];
                            if (points[s].compareTo(min) < 0)
                                min = points[s];

                            segments.add(new LineSegment(min, max));
                        }
                    }
    }

    private boolean unique(Point p, int pos) {
        for (int i = 0; i < pos; i++)
            if (this.points[i].compareTo(p) == 0)
                return false;
        return true;
    }

    public int numberOfSegments() // the number of line segments
    {
        return segments.size();
    }

    public LineSegment[] segments() // the line segments
    {
        LineSegment[] seg = new LineSegment[segments.size()];
        for (int i = 0; i < segments.size(); i++)
            seg[i] = segments.get(i);
        return seg;
    }
}
