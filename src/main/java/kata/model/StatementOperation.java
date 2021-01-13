package kata.model;

import java.math.BigDecimal;

public class StatementOperation {
    private final Operation operation;
    private final Number balanceAfterOperation;

    private StatementOperation(Operation operation, Number balanceAfterOperation) {
        this.operation = Operation.copy(operation);
        this.balanceAfterOperation = balanceAfterOperation;
    }

    public static StatementOperation from(Operation operation, Number balanceAfterOperation) {
        return new StatementOperation(operation, balanceAfterOperation);
    }

    public Operation getOperation() {
        return operation;
    }

    public Number getBalanceAfterOperation() {
        return balanceAfterOperation;
    }
}
