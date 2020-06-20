package eu.senla.course.util;

public class CsvException extends Exception {
    public CsvException(String message, Exception e) {
        super(message, e);
    }

    public CsvException(Exception e) {
        super(e);
    }

    public CsvException(String message) {
        super(message);
    }
}
