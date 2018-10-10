package persistence.exception;

public class PersistenceException extends Exception {

    private static final long serialVersionUID = -1847329412566948360L;

    public PersistenceException() {
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
