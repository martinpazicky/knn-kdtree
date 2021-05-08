package model;

import kdtree.KDTree;
import java.util.*;

public class KNNClassifier {

    /**
     * Classifies given point
     * @param x x coordinate of point
     * @param y y coordinate of point
     * @param k amount of neighbours
     * @param trainingSet set of points to influence the classification
     * @return color
     */
    public static byte classify(int x, int y, int k, KDTree trainingSet) {
         Point[] nearest = trainingSet.findNearestNeighbours(x,y,k);
         Map<Byte, Integer> counter = new HashMap<>();
         counter.put(Tester.R, 0);
         counter.put(Tester.G, 0);
         counter.put(Tester.B, 0);
         counter.put(Tester.P, 0);
         // counter will hold each color as key and their occurrence as value
         for(int i = 0; i < k; i++)
         {
              counter.put(nearest[i].color,counter.get(nearest[i].color) + 1);
         }

         // find value of maximum
         byte color = 1;
         for(byte i = 2; i < 5; i++)
             if(counter.get(i) > counter.get(color)) {
                 color = i;
             }
         int max = counter.get(color);
         // search the items for occurrence of another maximum
         // (this part can be optimized)
         byte[] colors = new byte[4];
         int index = 0;
         colors[index++] = color;
         for(byte i = 1; i < 5; i++)
         {
             if (i == color)
                 continue;
             if (counter.get(i) == max)
                 colors[index++] = i;
         }

         index--;
         // choose randomly from colors with max numbers of occurrence
         return colors[Main.rand.getRandomNum(0,index)];
    }

}

