package kata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User not authenticated")
public class ClientNotAuthenticatedException extends InvalidOperationException {
    public ClientNotAuthenticatedException(String message) {
        super(message);
    }
}
