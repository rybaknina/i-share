package eu.senla.course.exception;

public class InjectionException extends Exception {

    public InjectionException(String message, Exception e) {
        super(message, e);
    }

    public InjectionException(Exception e) {
        super(e);
    }

    public InjectionException(String message) {
        super(message);
    }
}
