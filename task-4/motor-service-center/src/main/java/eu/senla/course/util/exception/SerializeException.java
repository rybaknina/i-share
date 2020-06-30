package eu.senla.course.util.exception;

public class SerializeException extends Exception {
    public SerializeException(String message, Exception e) {
        super(message, e);
    }

    public SerializeException(Exception e) {
        super(e);
    }

    public SerializeException(String message) {
        super(message);
    }
}
