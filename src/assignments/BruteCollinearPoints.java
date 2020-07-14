import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<Point> startPoints;
    private final List<Point> endPoints;
    private final List<LineSegment> lineSegments;

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
        this.startPoints = new ArrayList<>();
        this.endPoints = new ArrayList<>();
        this.lineSegments = segmentsFromPoints(points);
    }

    private List<LineSegment> segmentsFromPoints(Point[] points) {
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
                    for (int n = k + 1; n < points.length; n++) {
                        Point s = points[n];
                        if (slopePQ == p.slopeTo(s)) {
                            int existingLineSegmentIndex = -1;
                            for (int previousSlopeIndex = 0; previousSlopeIndex < startPoints.size(); previousSlopeIndex++) {
                                Point startPoint = startPoints.get(previousSlopeIndex);
                                Point endPoint = endPoints.get(previousSlopeIndex);
                                double previousSlope = startPoint.slopeTo(endPoint);
                                if (previousSlope == slopePQ && startPoint.slopeTo(p) == slopePQ) {
                                    existingLineSegmentIndex = previousSlopeIndex;
                                    break;
                                }
                            }

                            if (existingLineSegmentIndex >= 0) {
                                // Existing segment
                                Point startPoint = startPoints.get(existingLineSegmentIndex);
                                Point endPoint = endPoints.get(existingLineSegmentIndex);
                                Point[] thisSlopePoints = {startPoint, p, q, r, s, endPoint};
                                Arrays.sort(thisSlopePoints);
                                startPoints.set(existingLineSegmentIndex, thisSlopePoints[0]);
                                endPoints.set(existingLineSegmentIndex, thisSlopePoints[5]);
                            } else {
                                // New Segment
                                Point[] thisSlopePoints = {p, q, r, s};
                                Arrays.sort(thisSlopePoints);
                                startPoints.add(thisSlopePoints[0]);
                                endPoints.add(thisSlopePoints[3]);
                            }

                        }
                    }
                }
            }
        }
        List<LineSegment> segments = new ArrayList<>();
        for (int i = 0; i < startPoints.size(); i++) {
            segments.add(new LineSegment(startPoints.get(i), endPoints.get(i)));
        }
        return segments;
    }


    public int numberOfSegments() {
        return startPoints.size();
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[startPoints.size()];
        for (int i = 0; i < startPoints.size(); i++) {
            segments[i] = lineSegments.get(i);
        }
        return segments;
    }

}
