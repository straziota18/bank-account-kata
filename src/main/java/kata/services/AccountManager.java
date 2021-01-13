package kata.services;

import kata.model.StatementOperation;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountManager {
    Number deposit(Number amount);

    Number withdraw(Number amount);

    default List<StatementOperation> printStatement() {
        return printStatement(null, null);
    }

    List<StatementOperation> printStatement(LocalDateTime startDate, LocalDateTime endDate);
}
