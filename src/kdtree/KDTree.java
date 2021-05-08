package kdtree;

import priorityqueue.CustomPQueue;
import model.Point;
import utility.Utility;

public class KDTree {
    private KDNode root = null;

    /**
     * getter for root node of the tree
     * @return root
     */
    public KDNode getRoot(){
        return this.root;
    }

    private KDNode insert(KDNode root, Point point, int depth)
    {
        if (root == null)
            return new KDNode(point.x, point.y, point.color);

        int toCompare = point.y;
        int toCompare2 = root.point.y;
        if (depth % Point.N == 0) {
            toCompare = point.x;
            toCompare2 = root.point.x;
        }

        if (toCompare < toCompare2)
            root.left = insert(root.left, point, depth + 1);
        else
            root.right = insert(root.right, point, depth + 1);

        return root;
    }

    /**
     * Inserts a point to KDTree
     * @param point point to insert
     */
    public void insert(Point point)
    {
        this.root = insert(root, point, 0);
    }

    /**
     * finds k nearest neighbours of a point
     * @param x x coordinate of point to find nearest neighbours to
     * @param y y coordinate of point to find nea   rest neighbours to
     * @param k amount of neighbours to find
     * @return array of k nearest neighbours
     */
    public Point[] findNearestNeighbours(int x, int y, int k)
    {
        // the priority queue will hold the k nearest neighbours
        CustomPQueue PQueue = new CustomPQueue(new Point(x,y), k);
        findNearestNeighbours(this.root, new Point(x,y), 0, PQueue, k);
        // transform priority queue to an array
        Point[] out = new Point[k];
        for (int i = 0;i<k;i++)
            out[i] = PQueue.poll();
            return out;
    }

    private void findNearestNeighbours(KDNode node, Point target, int depth, CustomPQueue PQueue, int k)
    {
        KDNode betterBranch, worseBranch;
        if (node == null)
            return;
        // select coordinate to compare based on depth
        int toCompare = target.y;
        int toCompare2 = node.point.y;
        if (depth % Point.N == 0) {
            toCompare = target.x;
            toCompare2 = node.point.x;
        }

        // decide which branch is better
        if (toCompare < toCompare2) {
            betterBranch = node.left;
            worseBranch = node.right;
        }
        else {
            betterBranch = node.right;
            worseBranch = node.left;
        }

        findNearestNeighbours(betterBranch,target,depth + 1,PQueue,k);
        // optimization - only add to PQueue if a node is better than the worst
        // code would also work without this condition because if worse node is added to PQueue it gets
        // popped immediately
        if (PQueue.size() < k || Utility.getDistanceSquared(node.point,target) < Utility.getDistanceSquared(PQueue.worst(),target))
            PQueue.add(node.point);

        double radius = Utility.getDistanceSquared(PQueue.worst(),target);
        double dist = toCompare - toCompare2;

        // follow the worseBranch if there could be any better point
        // or if enough neighbors were not yet found
        if (dist*dist <= radius || PQueue.size() < k) {
            findNearestNeighbours(worseBranch, target, depth + 1,PQueue,k);
        }
    }

    /**
     * Converts tree to array
     * @param n root of tree
     * @param results array with result
     * @param index helper variable for recursion, 0 should be initial value
     * @return array representation of the tree
     */
    public int treeToArray(KDNode n, Point[] results, int index) {
        if (n.left != null) {
            index = treeToArray(n.left, results, index);
        }
        if (n.right != null) {
            index = treeToArray(n.right, results, index);
        }
        results[index] = n.point;
        return index + 1;
    }
}
