package eu.senla.course.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {
    private static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                                    .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                                    .toFormatter();
    public static String readString(Scanner scanner, String message) {
        System.out.println(message);
        return readString(scanner);
    }
    public static String readString(Scanner scanner) {
        return scanner.next();
    }

    public static Integer readInteger(Scanner scanner, String message){
        System.out.println(message);
        return readInteger(scanner);
    }
    public static Integer readInteger(Scanner scanner){
        Integer number = null;
        boolean valid = false;
        while (!valid) {
            if (scanner.hasNextInt()){
                number = scanner.nextInt();
                valid = true;
            } else {
                System.out.println("Not a Number entered! Try again...");
                scanner.next();
                continue;
            }
        }
        return number;
    }
    public static LocalDateTime readDateTime(Scanner scanner, String message){
        System.out.println(message);
        return readDateTime(scanner);
    }
    public static LocalDateTime readDateTime(Scanner scanner){
        LocalDateTime dateTime = null;
        boolean valid = false;
        String line;
        while (!valid) {
            System.out.println("Enter date-time in format \"dd.MM.yyyy HH:mm\" or \"yyyy-MM-dd HH:mm\": ");
            if (scanner.hasNextLine()){
                line = scanner.nextLine();
                try {
                    dateTime = LocalDateTime.parse(line, formatter);
                    valid = true;
                } catch (DateTimeParseException ex){
                    System.out.println("Wrong date-time entered! Try again...");
                    scanner.next();
                    continue;
                }
            }
        }
        return dateTime;
    }

    public static BigDecimal readDecimal(Scanner scanner, String message){
        System.out.println(message);
        return readDecimal(scanner);
    }

    public static BigDecimal readDecimal(Scanner scanner){
        BigDecimal decimal = BigDecimal.ZERO;
        boolean valid = false;
        while (!valid) {
            if (scanner.hasNextBigDecimal()){
                decimal = scanner.nextBigDecimal();
                valid = true;
            } else {
                System.out.println("Not a BigDecimal entered! Try again...");
                scanner.next();
                continue;
            }
        }
        return decimal;
    }
}
