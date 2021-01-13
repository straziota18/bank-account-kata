package kata.model;

import java.time.LocalDateTime;

public class Operation {
    private final OperationType operationType;
    private final Number amount;
    private final LocalDateTime date;

    private Operation(OperationType operationType, Number amount, LocalDateTime date) {
        this.operationType = operationType;
        this.amount = amount;
        this.date = date;
    }

    public static Operation withdrawal(Number amount) {
        return new Operation(OperationType.WITHDRAWAL, amount, LocalDateTime.now());
    }

    public static Operation deposit(Number amount) {
        return new Operation(OperationType.DEPOSIT, amount, LocalDateTime.now());
    }

    public static Operation copy(Operation other) {
        return new Operation(other.operationType, other.amount, other.date);
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Number getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
