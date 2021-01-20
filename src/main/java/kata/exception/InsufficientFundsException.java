package kata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Insufficient funds")
public class InsufficientFundsException extends InvalidOperationException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
