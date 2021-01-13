package kata.services;

import kata.exception.InsufficientFundsException;
import kata.exception.InvalidOperationException;
import kata.model.Operation;
import kata.model.StatementOperation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountManagerImpl implements AccountManager {
    private final List<StatementOperation> operations = new ArrayList<>();

    public Number getBalance() {
        return operations.isEmpty() ? 0 : operations.get(operations.size() - 1).getBalanceAfterOperation();
    }

    @Override
    public Number deposit(Number amount) {
        if (amount.doubleValue() <= 0.0d) {
            throw new InvalidOperationException("Cannot deposit amount lower than 0");
        }
        var result = getBalance();
        StatementOperation newOperation = StatementOperation.from(Operation.deposit(amount),
                result.doubleValue() + amount.doubleValue());
        operations.add(newOperation);
        return newOperation.getBalanceAfterOperation();
    }

    @Override
    public Number withdraw(Number amount) {
        if (amount.doubleValue() <= 0.0d) {
            throw new InvalidOperationException("Cannot withdraw amount lower than 0");
        }
        var result = getBalance();
        if (result.doubleValue() - amount.doubleValue() < 0) {
            throw new InsufficientFundsException(String.format("Minimal value available for withdraw: %s", result));
        }
        StatementOperation newOperation = StatementOperation.from(Operation.withdrawal(amount),
                result.doubleValue() - amount.doubleValue());
        operations.add(newOperation);
        return newOperation.getBalanceAfterOperation();
    }

    @Override
    public List<StatementOperation> printStatement(LocalDateTime startDate, LocalDateTime endDate) {
        return operations.stream()
                .filter(it -> {
                    if (startDate != null && startDate.isAfter(it.getOperation().getDate())) {
                        return false;
                    }
                    return endDate == null || !endDate.isBefore(it.getOperation().getDate());
                })
                .collect(Collectors.toList());
    }
}
