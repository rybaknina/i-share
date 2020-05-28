package eu.senla.course;

import eu.senla.course.random.Generator;
import eu.senla.course.random.SumThreeDigitNumber;

/**
 * @author Nina Rybak
 * Программа выводит на экран:
 * - случайно сгенерированное трёхзначное натуральное число
 * - сумму его цифр
 */

public class TestSumDigits {
    public static void main(String[] args) {

        SumThreeDigitNumber sumThreeDigitNumber = new SumThreeDigitNumber(new Generator().generateNumber());

        System.out.println(sumThreeDigitNumber.toString());
    }
}
