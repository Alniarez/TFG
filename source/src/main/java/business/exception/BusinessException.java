package business.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = -2768306615722900880L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
