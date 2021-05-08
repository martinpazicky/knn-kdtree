package model;

import kdtree.KDTree;

public class Tester {
    public static final byte R = 1;
    public static final byte G = 2;
    public static final byte B = 3;
    public static final byte P = 4;
    final int lowerBoundary = -5000;
    final int upperBoundary = 5000;
    final int dataAmount;
    private KDTree dataSet = new KDTree();
    private int errors = 0;
    private final int k;

    /**
     * Constructor - enter algorithm parameters
     * @param k amount of neighbors
     * @param dataAmount amount of points to be generated
     */
    public Tester(int k, int dataAmount)
    {
        this.k = k;
        this.dataAmount = dataAmount;
    }



    private void addInitialItems()
    {
        addPoint(-4500, -4400, R);
        addPoint(-4100, -3000, R);
        addPoint(-1800, -2400, R);
        addPoint(-2500, -3400, R);
        addPoint(-2000, -1400, R);
        addPoint(4500, -4400, G);
        addPoint(4100, -3000, G);
        addPoint(1800, -2400, G);
        addPoint(2500, -3400, G);
        addPoint(2000, -1400, G);
        addPoint(-4500, 4400, B);
        addPoint(-4100, 3000, B);
        addPoint(-1800, 2400, B);
        addPoint(-2500, 3400, B);
        addPoint(-2000, 1400, B);
        addPoint(4500, 4400, P);
        addPoint(4100, 3000, P);
        addPoint(1800, 2400, P);
        addPoint(2500, 3400, P);
        addPoint(2000, 1400, P);
    }

    private void addPoint(int x, int y, byte color) {
        dataSet.insert(new Point(x,y,color));
    }

    /**
     * Generates a point from specified interval with 99% chance
     * @param x1L lower interval boundary for x
     * @param x1U upper interval boundary for x
     * @param y1L lower interval boundary for y
     * @param y1U upper interval boundary for y
     * @param color desired color of the point
     * @return newly generated point
     */
    private Point generatePoint(int x1L, int x1U, int y1L, int y1U, byte color)
    {
        int x,y;
        if (Main.rand.random.nextDouble() < 0.99) {
            x = Main.rand.getRandomNum(x1L, x1U);
            y = Main.rand.getRandomNum(y1L, y1U);
        }
        else {
            // generate coordinates until the point does not belong to specified interval
            do {
                x = Main.rand.getRandomNum(lowerBoundary, upperBoundary);
                y = Main.rand.getRandomNum(lowerBoundary, upperBoundary);
            } while (x >= x1L && x <= x1U && y >= y1L && y <= y1U);
        }
        return new Point(x,y,color);
    }

    private void processPoint(Point point)
    {
        byte newColor = KNNClassifier.classify(point.x, point.y, k, dataSet);
        addPoint(point.x, point.y, newColor);
        if (newColor != point.color)
            errors++;
    }

    private void generateData()
    {
        Point point;
        // generates the points in R G B P order
        for(int i = 0; i < dataAmount; i+=4)
        {
            point = generatePoint(-5000,499,-5000,499,R);
            processPoint(point);
            point = generatePoint(-499,5000,-5000,499,G);
            processPoint(point);
            point = generatePoint(-5000,499,-499,5000,B);
            processPoint(point);
            point = generatePoint(-499,5000,-499,5000,P);
            processPoint(point);
        }
    }

    /**
     * run solver
     */
    public void run()
    {
        addInitialItems();
        generateData();
    }

    /**
     * getter
     * @return number of classification errors
     */
    public int getErrors()
    {
        return this.errors;
    }

    /**
     * getter
     * @return dataset
     */
    public KDTree getDataSet()
    {
        return this.dataSet;
    }

}
