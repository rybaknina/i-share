package by.ryni.share.ecxeption;

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
