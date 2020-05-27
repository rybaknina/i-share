package random;

/**
 * @author Nina Rybak
 * Программа выводит на экран:
 * - случайно сгенерированное трёхзначное натуральное число
 * - сумму его цифр
 */

public class TestSumDigits {
    public static void main(String[] args) {

        SumThreeDigitNumber object = new SumThreeDigitNumber();

        int number = object.generateNumber();
        int sum = object.sumDigitsInNumber(number);
        object.printNumber(number, sum);
    }
}
