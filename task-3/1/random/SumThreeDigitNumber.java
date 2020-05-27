package random;

import java.util.Random;


public class SumThreeDigitNumber {
    private static final int RANDOM_MIN = 100;
    private static final int RANDOM_MAX = 999;

    public int generateNumber(){
        return new Random().nextInt(RANDOM_MAX - RANDOM_MIN + 1) + RANDOM_MIN;
    }
    public int sumDigitsInNumber(int number){
        int sum = 0;
        do {
            sum += number % 10;
            number /= 10;
        } while (number != 0);
        return sum;
    }

    public void printNumber(int randomNumber, int sumDigits) {
        System.out.printf("Random number: %s%nSum of numbers: %s%n", randomNumber, sumDigits);
    }

}
