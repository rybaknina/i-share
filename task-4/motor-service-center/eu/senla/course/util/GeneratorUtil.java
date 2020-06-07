package eu.senla.course.util;

import java.util.Random;

public class GeneratorUtil {
    private static final int RANDOM_MIN = 1;
    private static final int RANDOM_MAX = 4;

    public static int generateNumber(){
        return new Random().nextInt(RANDOM_MAX - RANDOM_MIN + 1) + RANDOM_MIN;
    }
}
