package kata.model;

import java.util.function.BinaryOperator;

public enum OperationType {
    WITHDRAWAL((x, y) -> x.doubleValue() - y.doubleValue()),
    DEPOSIT((x, y) -> x.doubleValue() + y.doubleValue());

    public final BinaryOperator<Number> accumulator;

    OperationType(BinaryOperator<Number> accumulator) {
        this.accumulator = accumulator;
    }
}
