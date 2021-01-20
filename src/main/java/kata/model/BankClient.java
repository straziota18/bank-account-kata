package kata.model;

import java.security.Principal;
import java.util.Objects;

public class BankClient implements Principal {
    private String userId;

    public BankClient() {
    }

    @Override
    public String getName() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankClient that = (BankClient) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
