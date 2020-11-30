package by.ryni.share.exception;

public class RepositoryException extends Exception {

    public RepositoryException(String message, Exception e) {
        super(message, e);
    }

    public RepositoryException(Exception e) {
        super(e);
    }

    public RepositoryException(String message) {
        super(message);
    }
}
