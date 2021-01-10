import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class BruteCollinearPoints {
    private final Point[] cPoints;
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {

    	// Check argument
    	if (points == null) {
    		throw new IllegalArgumentException("Point list should not be null.");
    	}
    	if (points.length < 1) {
    		throw new IllegalArgumentException("Point list should not be empty.");
    	}
    	List<String> pntStrings = new ArrayList<String>();
    	
    	for (int i = 0; i < points.length; i++) {
    		if (points[i] == null) {
    			throw new IllegalArgumentException("Point list's " + i 
    					+ "th element is null");
    		}
    		/* It is bad style to write code that depends on the particular format of
    		 * the output from the toString() method, especially if your reason for
    		 * doing so is to circumvent the public API (which intentionally does not
    		 * provide access to the x- and y-coordinates).*/
//    		for (int j = i + 1; j < points.length; j++) {
//    			if (points[i].toString().equals(points[j].toString()))
//    				throw new IllegalArgumentException("Point list has repeat entries.");
//    		}
    		if (pntStrings.contains(points[i].toString())) {
                throw new IllegalArgumentException("Point list has repeat entries.");
            } else {
            	pntStrings.add(points[i].toString());
            }
    	}
    	
    	// Initialize
    	cPoints = new Point[points.length];
    	for (int i = 0; i < points.length; i++) {
    		cPoints[i] = points[i];
    		
    	}
    	
    	lineSegments = segments();
     
    }


    public int numberOfSegments() {
    	if (lineSegments == null)
    		return 0;
        return lineSegments.length;
    }
    // the line segments
    // just consider 4 points
    public LineSegment[] segments() {
    	
    	// just a trick to avoid segments() called with different results
    	// not a good idea
    	if (numberOfSegments() > 0)
    		return lineSegments;
    	
    	List<LineSegment> lineSegmentsList = new ArrayList<LineSegment>(); ; // collinear line segments


        int pointsNum = cPoints.length;

        for (int i = 0; i < pointsNum - 3; i++) {
            Point p1 = cPoints[i];

            for (int j = i + 1; j < pointsNum - 2; j++) {
                Point p2 = cPoints[j];
                double p12 = p1.slopeTo(p2);

                for (int k = j + 1; k < pointsNum - 1; k++) {
                    Point p3 = cPoints[k];
                    double p13 = p1.slopeTo(p3);

                    if (!(p12 == p13)) continue;

                    for (int l = k + 1; l < pointsNum; l++) {
                        Point p4 = cPoints[l];
                        double p14 = p1.slopeTo(p4);

                        if (!(p12 == p14)) continue;
                        
                        // sort 4 points 
                        List<Point> collinearPoints = new ArrayList<Point>();
                        collinearPoints.add(p1);
                        collinearPoints.add(p2);
                        collinearPoints.add(p3);
                        collinearPoints.add(p4);
                        
                        // call the Point.compareTo()
                        Collections.sort(collinearPoints);
                        Point leftPoint = collinearPoints.get(0);
                        Point rightPoint = collinearPoints.get(3);

                        lineSegmentsList.add(new LineSegment(leftPoint, rightPoint));

                    }
                }
            }
        }
    	
    	return lineSegmentsList.toArray(new LineSegment[lineSegmentsList.size()]);

    }
}

