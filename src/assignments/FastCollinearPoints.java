import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FastCollinearPoints {
    private final List<Point> startPoints;
    private final List<Point> endPoints;

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
                if (p.equals(points[j])) {
                    throw new IllegalArgumentException();
                }
            }
        }

        this.startPoints = new ArrayList<>();
        this.endPoints = new ArrayList<>();
        segmentsFromPoints(points.clone());
    }

    private void segmentsFromPoints(Point[] points) {
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
                    if (!(startPoints.contains(startPoint) && endPoints.contains(endPoint))) {
                        startPoints.add(startPoint);
                        endPoints.add(endPoint);
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return startPoints.size();
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[startPoints.size()];
        for (int i = 0; i < startPoints.size(); i++) {
            segments[i] = new LineSegment(startPoints.get(i), endPoints.get(i));
        }
        return segments;
    }
}
