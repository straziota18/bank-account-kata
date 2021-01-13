package kata.exception;

public class InsufficientFundsException extends InvalidOperationException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
