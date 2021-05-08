package utility;

import model.Point;

public class Utility {
    /**
     * Counts euclidean distance between 2 points
     * @param a point1
     * @param b point2
     * @return distance
     */
    public static double getDistanceSquared(Point a, Point b) {
        return (b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x);
    }

    public static double nanoSecToSec(double nanoSeconds)
    {
        return nanoSeconds / 1000000000;
    }

}

