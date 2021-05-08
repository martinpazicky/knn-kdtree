package priorityqueue;

import model.Point;
import utility.Utility;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Class represents priority queue with bounded max size and custom comparing method
 * Organization of priority queue is based on distances to given target point
 * The more distant a point is from target point the closer to the top it is (max heap)
 **/
public class CustomPQueue {
    /**
     * Custom comparator for priority queue
     * compares distances to given target point
     */
    private static class distanceComparator implements Comparator<Point> {

        private Point target;

        /**
         * Constructor
         * @param target the deciding point
         */
        public distanceComparator(Point target) {
            this.target = target;
        }

        @Override
        public int compare(Point a, Point b) {
            return Double.compare(Utility.getDistanceSquared(b, target), Utility.getDistanceSquared(a, target));
        }
    }

    private PriorityQueue<Point> priorityQueue;
    private final int k;

    /**
     * Constructor
     * @param target the deciding point of priority queue
     * @param k max size
     */
    public CustomPQueue(Point target, int k)
    {
        this.priorityQueue = new PriorityQueue<>(new distanceComparator(target));
        this.k = k;
    }

    /**
     * adds a point to priority queue, if max size was exceeded, polls the worst point
     * @param p point to be added
     */
    public void add(Point p)
    {
        priorityQueue.add(p);
        if(priorityQueue.size() > k)
            priorityQueue.poll();
    }

    /**
     * returns the top of the priority queue (peek method)
     * @return top element of priority queue
     */
    public Point worst()
    {
        return priorityQueue.peek();
    }

    public int size()
    {
        return priorityQueue.size();
    }

    public Point poll()
    {
        return priorityQueue.poll();
    }

}
