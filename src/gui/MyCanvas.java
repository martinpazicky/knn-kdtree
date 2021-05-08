package gui;
import kdtree.KDTree;
import model.KNNClassifier;
import model.Point;
import model.Tester;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MyCanvas extends Canvas {
    private Point[] toPaint;
    private KDTree finalDataSet = null;
    private int k;
    Map<Byte,Color> colors = new HashMap<>();
    private int width = 770;
    private int height = 770;

    // this constructor is used when painting only points
    public MyCanvas(Point[] toPaint)
    {
        initializeColors();
        this.toPaint = toPaint;
        setBackground (Color.WHITE);
        setBounds(100,50,width,height);
    }

    //this constructor is used when painting the whole area
    public MyCanvas(Point[] toPaint,KDTree data, int k)
    {
        initializeColors();
        this.toPaint = toPaint;
        this.finalDataSet = data;
        this.k = k;
        Color c = new Color(0.94f,0.94f,0.86f);
        setBackground (c);
        setBounds(100,50,770,770);
    }

    private void initializeColors()
    {
        colors.put((byte)1,Color.red);
        colors.put((byte)2,Color.green);
        colors.put((byte)3,Color.blue);
        colors.put((byte)4,Color.magenta);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        // only print generated points
        for (Point point2D : toPaint) {
            g2.setColor(colors.get(point2D.color));
            // transpose coordinates to fit into screen
            int x = ((point2D.x + 5000) / 13);
            int y = ((point2D.y + 5000) / 13);
            g2.drawLine(x, y, x, y);
        }
        // also paint all points on the area with transparent colors
        if (this.finalDataSet != null) {
            Color rt = new Color(1f, 0f, .0f, 0.3f);
            Color gt = new Color(0f, 1f, .05f, 0.3f);
            Color bt = new Color(0f, 0f, 1f, 0.3f);
            Color pt = new Color(0.9f, 0f, .67f, 0.3f);
            for (int x = 0; x < width; x+=3) {
                for (int y = 0; y < height; y+=3) {
                    byte color = KNNClassifier.classify(x * 13 - 5000, y * 13 - 5000, k, finalDataSet);
                    if (color == Tester.R)
                        g2.setColor(rt);
                    if (color == Tester.G)
                        g2.setColor(gt);
                    if (color == Tester.B)
                        g2.setColor(bt);
                    if (color == Tester.P)
                        g2.setColor(pt);
                    g2.drawLine(x, y, x, y);
                }
            }
        }
    }
}
