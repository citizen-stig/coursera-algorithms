import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FastCollinearPoints {
    private final List<Point> startPoints;
    private final List<Point> endPoints;
    private final List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
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
                if (p.compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        this.startPoints = new ArrayList<>();
        this.endPoints = new ArrayList<>();
        this.lineSegments = segmentsFromPoints(points.clone());
    }

    private List<LineSegment> segmentsFromPoints(Point[] points) {
        Point[] originalPoints = points.clone();
        for (int i = 0; i < points.length - 3; i++) {
            Point p = originalPoints[i];
            for (int j = i + 1; j < points.length - 2; j++) {
                Point q = originalPoints[j];
                double slopePQ = p.slopeTo(q);
                Arrays.sort(points, p.slopeOrder());
                List<Point> pointsWithSameSlope = Arrays
                        .stream(points)
                        .filter(point -> p.slopeTo(point) == slopePQ)
                        .collect(Collectors.toList());
                if (pointsWithSameSlope.size() >= 3) {
                    pointsWithSameSlope.add(p);
                    Collections.sort(pointsWithSameSlope);

                    Point startPoint = pointsWithSameSlope.get(0);
                    Point endPoint = pointsWithSameSlope.get(pointsWithSameSlope.size() - 1);

                    // Check existing lines:
                    if (isLineSegmentAlreadyExists(startPoint, endPoint)) {
                        continue;
                    }

                    startPoints.add(startPoint);
                    endPoints.add(endPoint);
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

    private boolean isLineSegmentAlreadyExists(Point startPoint, Point endPoint) {
        for (int index = 0; index < startPoints.size(); index++) {
            if (startPoints.get(index).equals(startPoint) && endPoints.get(index).equals(endPoint)) {
                return true;
            }
        }
        return false;
    }
}
