import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FastCollinearPoints {
    private final List<Point> startPoints;
    private final List<Point> endPoints;
    private final List<Double> slopes;

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
        this.slopes = new ArrayList<>();
        segmentsFromPoints(points.clone());
    }

    private void segmentsFromPoints(Point[] points) {
        Point[] originalPoints = points.clone();
        for (int i = 0; i < points.length - 3; i++) {
            Point p = originalPoints[i];
            for (int j = i + 1; j < points.length - 2; j++) {
                Point q = originalPoints[j];
                double slopePQ = p.slopeTo(q);
                if (slopes.contains(slopePQ)) {
                    continue;
                }
                Arrays.sort(points, p.slopeOrder());
                List<Point> pointsWithSameSlope = Arrays
                        .stream(points)
                        .filter(point -> p.slopeTo(point) == slopePQ)
                        .sorted()
                        .collect(Collectors.toList());
                if (pointsWithSameSlope.size() >= 3) {
                    startPoints.add(pointsWithSameSlope.get(0));
                    endPoints.add(pointsWithSameSlope.get(pointsWithSameSlope.size() - 1));
                }
                slopes.add(slopePQ);
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
