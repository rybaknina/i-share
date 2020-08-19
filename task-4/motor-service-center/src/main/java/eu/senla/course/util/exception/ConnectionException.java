package eu.senla.course.util.exception;

public class ConnectionException extends Exception {

    public ConnectionException(String message, Exception e) {
        super(message, e);
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Exception e) {
        super(e);
    }
}
