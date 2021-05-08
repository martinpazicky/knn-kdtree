package model;

import gui.MyCanvas;
import kdtree.KDTree;
import utility.RandomGenerator;
import utility.Utility;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;


public class Main {

    public static void visualize(Point[] toPaint,String title)
    {
         MyCanvas canvas = new MyCanvas(toPaint);
         Frame f = new Frame(title);
         f.add(canvas);
         f.setLayout(null);
         f.setSize(1000, 900);
         f.setVisible(true);
         f.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent we) {
                f.dispose();
            }
        });
    }

    public static void visualize(Point[] toPaint,KDTree data,String title, int k)
    {
        MyCanvas canvas = new MyCanvas(toPaint,data,k);
        Frame f = new Frame(title);
        f.add(canvas);
        f.setLayout(null);
        f.setSize(1000, 900);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                f.dispose();
            }
        });
    }

    private static void userDialog()
    {
        Scanner sc = new Scanner(System.in);
        int counter = 1;
        while (true)
        {
            System.out.println("1 - spustit program\n2 - koniec");
            String choice = sc.nextLine();
            if (choice.equals("1"))
            {
                System.out.println("zadaj hodnotu k (1 az 20)");
                int k = Integer.parseInt(sc.nextLine());
                int dataAmount;
                do {
                    System.out.println("zadaj mnozstvo bodov ktore chces generovat (MUSI BYT DELITELNE 4!)\n" +
                            "odporucane mnozstvo bodov je 40000 (zadanie)");
                    dataAmount = Integer.parseInt(sc.nextLine());
                } while (dataAmount % 4 != 0);
                System.out.println("zadaj seed generatora nahodnych cisel (hociake cislo)");
                int seed = Integer.parseInt(sc.nextLine());
                rand = new RandomGenerator(seed);
                Tester tester = new Tester(k, dataAmount);
                double start = System.nanoTime();
                tester.run();
                double end = System.nanoTime();
                System.out.printf("-- vygenerovanie bodov trvalo %.3f sek\n",  Utility.nanoSecToSec(end - start));
                System.out.printf("-- %d bodom bola chybne urcena farba (uspesnost %.2f %%)\n", tester.getErrors(),
                            (100 - tester.getErrors()/(double)dataAmount*100));
                KDTree tree = tester.getDataSet();
                Point[] data = new Point[dataAmount + 20];
                tree.treeToArray(tree.getRoot(), data, 0);
                String title = "Canvas " + counter++ + ": k = " + k;
                System.out.println("zvol sposob vizualizacie\n1 - vykreslit len vygenerovane body" +
                        "\n2 - zafarbit celu plochu (moze trvat dlhsie kvoli vykreslovaniu transparentnych bodov)");
                String visualisation = sc.nextLine();
                if (visualisation.equals("1")) {
                    visualize(data, title);
                    System.out.println("-- body sa teraz vykresluju v druhom okne...");
                }
                else if(visualisation.equals("2")) {
                    visualize(data, tree, title + " areas", k);
                    System.out.println("body sa teraz vykresluju v druhom okne...");
                }
            }
            else if (choice.equals("2"))
            {
                break;
            }
            else System.out.println("neplatna volba");
        }
    }
    // random generator shared among all classes
    public static RandomGenerator rand;
    public static void test(int testAmount, int k)
    {
        double time = 0;
        long errors = 0;
        for (int i = 0; i < testAmount; i++) {
            // dataAmount must be divisible by 4 (same amount of each color)
            rand = new RandomGenerator(i);
            int dataAmount = 40000;
            Tester tester = new Tester(k, dataAmount);
            double start = System.nanoTime();
            tester.run();
            double end = System.nanoTime();
            System.out.println(tester.getErrors());
            System.out.println(Utility.nanoSecToSec(end - start));
            time += Utility.nanoSecToSec(end - start);
            errors += (tester.getErrors());
        }

        System.out.println("avg time: " +  time/testAmount);
        System.out.println("avg errors: " + errors/testAmount + " -> success rate: " +
                (100 - errors/(testAmount*(double)40000)*100));
    }
    public static void main(String[] args) {
        userDialog();
//        test(3, 15);
    }
}
