import java.util.Arrays;
import java.util.ArrayList;

/**
 * @author svoit
 */
public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    /**
     * finds all line segments containing 4 points
     *
     * @param points
     *
     * @throws IllegalArgumentException if either argument is null, or array contains null point",
     * or array contains a repeated point
     */
    public FastCollinearPoints(Point[] points) {
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

        for (int i = 0; i < n; i++) {
            Point[] p = ps.clone();
            Arrays.sort(p, p[i].slopeOrder());

            int j = 1;
            while (j < n - 2) {
                int k = j;
                double s1 = p[0].slopeTo(p[k++]);
                double s2;

                do {
                    if (k == n) {
                        k++;
                        break;
                    }
                    s2 = p[0].slopeTo(p[k++]);
                } while (Double.compare(s1, s2) == 0);

                if (k - j < 4) {
                    j++;
                    continue;
                }

                int length = k-- - j;
                Point[] line = new Point[length];
                line[0] = p[0];

                for (int m = 1; m < length; m++) {
                    line[m] = p[j + m - 1];
                }
                Arrays.sort(line);
                // remove duplicate
                if (line[0] == p[0]) {
                    list.add(new LineSegment(line[0], line[length - 1]));
                }
                j = k;
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
