import java.util.Arrays;
import java.util.ArrayList;

/**
 * @author svoit
 */
public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    /**
     * finds all line segments containing 4 points
     *
     * @param points
     *
     * @throws IllegalArgumentException if either argument is null, or array contains null point",
     * or array contains a repeated point
     */
    public BruteCollinearPoints(Point[] points) {
        // Corner cases
        if (points == null)
            throw new IllegalArgumentException("argument is null");

        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException("array contains null point");
        }

        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("array contains a repeated point");
            }
        }

        Point[] ps = points.clone();
        Arrays.sort(ps);
        ArrayList<LineSegment> list = new ArrayList<LineSegment>();
        // Brute force
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int m = k + 1; m < n; m++) {

                        Point[] p = new Point[4];
                        p[0] = ps[i];
                        p[1] = ps[j];
                        p[2] = ps[k];
                        p[3] = ps[m];

                        double s1 = p[0].slopeTo(p[1]);
                        double s2 = p[0].slopeTo(p[2]);

                        if (Double.compare(s1, s2) != 0) continue;

                        double s3 = p[0].slopeTo(p[3]);
                        if (Double.compare(s1, s3) == 0) {
                            Arrays.sort(p);
                            list.add(new LineSegment(p[0], p[3]));
                        }
                    }
                }
            }
        }
        // transform to array
        lineSegments = list.toArray(new LineSegment[list.size()]);
    }

    /**
     * return the number of line segments
     *
     * @return number of the segments
     */
    public int numberOfSegments() {
        return lineSegments.length;
    }

    /**
     * return the line segments
     *
     * @return line segments
     */
    public LineSegment[] segments() {
        return lineSegments.clone();
    }
}
