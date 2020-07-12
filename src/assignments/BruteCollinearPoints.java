import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            // No null points
            if (p == null) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < points.length; j++) {
                if (i == j) {
                    continue;
                }
                // No same points
                if (p.equals(points[j])) {
                    throw new IllegalArgumentException();
                }
            }
        }
        List<LineSegment> segments = segmentsFromPoints(points);
        this.segments = new LineSegment[segments.size()];
        for (int i = 0; i < segments.size(); i++) {
            this.segments[i] = segments.get(0);
        }
    }

    private List<LineSegment> segmentsFromPoints(Point[] points) {
        Double[] slopes = new Double[points.length];
        LineSegment[] ls = new LineSegment[points.length];
        int lineSegmentIndex = 0;
        for (int i = 0; i < points.length - 3; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length - 2; j++) {
                Point q = points[j];
                double slopePQ = p.slopeTo(q);
                for (int k = j + 1; k < points.length - 1; k++) {
                    Point r = points[k];
                    if (p.slopeTo(r) != slopePQ) {
                        continue;
                    }
                    for (int l = k + 1; l < points.length; l++) {
                        Point s = points[l];
                        if (slopePQ == p.slopeTo(s)) {
                            Integer existingLineSegmentIndex = null;
                            for (int slopeIndex = 0; slopeIndex < slopes.length; slopeIndex++) {
                                if (slopes[slopeIndex] != null && slopes[slopeIndex] == slopePQ) {
                                    existingLineSegmentIndex = slopeIndex;
                                    break;
                                }
                            }
                            if (existingLineSegmentIndex != null) {
                                ls[existingLineSegmentIndex] = new LineSegment(p, s);
                            } else {
                                slopes[lineSegmentIndex] = slopePQ;
                                ls[lineSegmentIndex++] = new LineSegment(p, s);
                            }

                        }
                        // System.out.println(
                        //         String.format("i: %s; j: %s, k: %s, l: %s", i, j, k, l)
                        // );
                        // if (isOnTheSameLineSegment(points[i], points[j], points[k], points[l])) {
                        //     lineSegments.add(
                        //             new LineSegment(points[i], points[l])
                        //     );
                        // }
                    }
                }
            }
        }
        List<LineSegment> lineSegments = new ArrayList<>();
        for (LineSegment l : ls) {
            if (l != null) {
                lineSegments.add(l);
            }
        }
        return lineSegments;
}

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }
}
