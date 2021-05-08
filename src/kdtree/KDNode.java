package kdtree;

import model.Point;

/**
 * Representation of a KDTree node
 */
public class KDNode {
    public final Point point;
    public KDNode left = null;
    public KDNode right = null;
    public KDNode(int x, int y, byte color)
    {
        this.point = new Point(x,y,color);
    }
}
