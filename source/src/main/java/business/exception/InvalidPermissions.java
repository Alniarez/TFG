package business.exception;

public class InvalidPermissions extends Exception {

    private static final long serialVersionUID = -2589028779207895101L;

    public InvalidPermissions(String message) {
        super(message);
    }

    public InvalidPermissions(String message, Throwable cause) {
        super(message, cause);
    }

}
