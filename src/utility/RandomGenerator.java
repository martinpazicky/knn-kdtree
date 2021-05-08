package utility;

import java.util.Random;

public class RandomGenerator {
    public Random random;

    public RandomGenerator(int seed){
        this.random = new Random(seed);
    }

    public RandomGenerator(){
        this.random = new Random();
    }

    public int getRandomNum(int min, int max)
    {
        return random.nextInt(max - min + 1) + min;
    }
}
