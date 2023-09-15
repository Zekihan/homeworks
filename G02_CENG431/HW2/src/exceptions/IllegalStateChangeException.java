package exceptions;

public class IllegalStateChangeException extends Exception{
    public IllegalStateChangeException(String errorMessage) {
        super(errorMessage);
    }
}
