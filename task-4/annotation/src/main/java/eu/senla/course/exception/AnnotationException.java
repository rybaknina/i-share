package eu.senla.course.exception;

public class AnnotationException extends Exception {

    public AnnotationException(String message, Exception e) {
        super(message, e);
    }

    public AnnotationException(Exception e) {
        super(e);
    }

    public AnnotationException(String message) {
        super(message);
    }
}
