import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    List<Double> slopes;
    List<Point> startPoints;
    List<Point> endPoints;

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
        this.slopes = new ArrayList<>();
        this.startPoints = new ArrayList<>();
        this.endPoints = new ArrayList<>();
        segmentsFromPoints(points);
    }

    private void segmentsFromPoints(Point[] points) {
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
                            for (int slopeIndex = 0; slopeIndex < slopes.size(); slopeIndex++) {
                                if (slopes.get(slopeIndex) == slopePQ) {
                                    existingLineSegmentIndex = slopeIndex;
                                    break;
                                }
                            }
                            if (existingLineSegmentIndex != null) {
                                // Existing segment
                                Point startPoint = startPoints.get(existingLineSegmentIndex);
                                Point endPoint = endPoints.get(existingLineSegmentIndex);
                                Point[] thisSlopePoints = {startPoint, p, q, r, s, endPoint};
                                Arrays.sort(thisSlopePoints);
                                startPoints.set(existingLineSegmentIndex, thisSlopePoints[0]);
                                endPoints.set(existingLineSegmentIndex, thisSlopePoints[5]);
                            } else {
                                // New Segment
                                slopes.add(slopePQ);
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
