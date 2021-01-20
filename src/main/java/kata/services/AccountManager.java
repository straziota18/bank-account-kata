package kata.services;

import kata.model.StatementOperation;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AccountManager {
    void deposit(Number amount);

    void withdraw(Number amount);

    List<StatementOperation> printStatement();

    Number getBalance();
}
