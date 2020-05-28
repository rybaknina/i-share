package eu.senla.course.random;

public class SumThreeDigitNumber {

    private int number;
    private int sum;

    public SumThreeDigitNumber(int number) {
        this.number = number;
        this.sum = sumDigitsInNumber(number);
    }

    public int sumDigitsInNumber(int number){
        int sum = 0;
        do {
            sum += number % 10;
            number /= 10;
        } while (number != 0);
        return sum;
    }

    @Override
    public String toString() {
        return "Random number = " + number +
                "\nSum of numbers = " + sum;
    }
}
