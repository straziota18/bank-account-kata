package kata.services;

import kata.exception.InsufficientFundsException;
import kata.exception.InvalidOperationException;
import kata.dao.DatabaseAccess;
import kata.model.Operation;
import kata.model.OperationType;
import kata.model.StatementOperation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class AccountManagerImpl implements AccountManager {

    @Autowired
    private DatabaseAccess databaseAccess;

    @Override
    public Number getBalance() {
        return databaseAccess.getAllOperations()
            .map(op -> op.getOperationType() == OperationType.DEPOSIT ? op.getAmount().doubleValue() : - op.getAmount().doubleValue())
            .reduce(0.0, Double::sum);
    }

    @Override
    public void deposit(Number amount) {
        if (amount.doubleValue() <= 0.0d) {
            throw new InvalidOperationException("Cannot deposit amount lower than 0");
        }
        databaseAccess.addOperation(Operation.deposit(amount));
    }

    @Override
    public void withdraw(Number amount) {
        if (amount.doubleValue() <= 0.0d) {
            throw new InvalidOperationException("Cannot withdraw amount lower than 0");
        }
        var result = getBalance();
        if (result.doubleValue() - amount.doubleValue() < 0) {
            throw new InsufficientFundsException(String.format("Minimal value available for withdraw: %s", result));
        }
        databaseAccess.addOperation(Operation.withdrawal(amount));
    }

    @Override
    public List<StatementOperation> printStatement() {
        final var currentBalance = new AtomicReference<Number>(0.0);
        return databaseAccess.getAllOperations()
                .sorted(Comparator.comparing(Operation::getDate))
                .map(it -> {
                    currentBalance.getAndAccumulate(it.getAmount(), it.getOperationType().accumulator);
                    return StatementOperation.from(it, currentBalance.get());
                })
                .collect(Collectors.toList());
    }
}
