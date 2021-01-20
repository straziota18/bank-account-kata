package kata.configuration;

import kata.exception.ClientNotAuthenticatedException;
import kata.model.BankClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private BankClient bankClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("user-id");
        if (Objects.isNull(userId)) {
            throw new ClientNotAuthenticatedException("Header \"user-id\" missing in request");
        }
        bankClient.setUserId(userId);
        return true;
    }
}
