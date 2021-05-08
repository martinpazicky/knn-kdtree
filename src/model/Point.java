package model;

/**
 * Class represents 2D point
 */
public class Point {
    public final int x,y;
    public byte color;
    // amount of dimensions
    public static final int N = 2;

    public Point(int x, int y, byte color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.color = 0;
    }
}
