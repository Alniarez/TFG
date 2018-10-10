package business.exception;

public class InvalidLoginCredentials extends Exception {

    private static final long serialVersionUID = -2589028779207895101L;

    public InvalidLoginCredentials(String message) {
        super(message);
    }

    public InvalidLoginCredentials(String message, Throwable cause) {
        super(message, cause);
    }

}
