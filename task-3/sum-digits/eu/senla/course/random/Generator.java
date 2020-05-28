package eu.senla.course.random;

import java.util.Random;

public class Generator {
    private static final int RANDOM_MIN = 100;
    private static final int RANDOM_MAX = 999;

    public int generateNumber(){
        return new Random().nextInt(RANDOM_MAX - RANDOM_MIN + 1) + RANDOM_MIN;
    }
}
