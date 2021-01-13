package kata.services;

import kata.exception.InsufficientFundsException;
import kata.exception.InvalidOperationException;
import kata.model.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

class AccountManagerImplTest {

    @Test
    void printStatement_should_return_empty_when_there_are_no_operations() {
        AccountManager service = new AccountManagerImpl();

        Assertions.assertTrue(service.printStatement().isEmpty());
    }

    @Test()
    void withdraw_should_return_exception_when_there_are_insufficient_funds() {
        AccountManager service = new AccountManagerImpl();

        Assertions.assertThrows(InsufficientFundsException.class, () -> service.withdraw(1));
    }

    @Test()
    void deposit_and_withdrawal_should_fail_for_negative_amounts() {
        AccountManager service = new AccountManagerImpl();

        Arrays.asList(0, -1).forEach(amount -> {
            Assertions.assertThrows(InvalidOperationException.class, () -> service.withdraw(amount));
            Assertions.assertThrows(InvalidOperationException.class, () -> service.deposit(amount));
        });
    }

    @Test()
    void withdrawal_with_insufficient_funds_should_throw_exception() {
        AccountManager service = new AccountManagerImpl();

        Assertions.assertThrows(InsufficientFundsException.class, () -> service.withdraw(10));

        final var initialDeposit = 100d;
        Assertions.assertEquals(initialDeposit, service.deposit(initialDeposit));

        Assertions.assertEquals(75d, service.withdraw(25d));
    }

    @Test()
    void printStatement_should_return_operations_made() {
        AccountManager service = new AccountManagerImpl();

        final var beforeOperationsTimestamp = LocalDateTime.now();

        Assertions.assertEquals(10d, service.deposit(10));
        Assertions.assertEquals(30d, service.deposit(20));
        Assertions.assertEquals(25d, service.withdraw(5));
        Assertions.assertThrows(InsufficientFundsException.class, () -> service.withdraw(25.01));
        Assertions.assertEquals(0d, service.withdraw(25));

        final var statement = service.printStatement();

        Assertions.assertEquals(4, statement.size());

        Assertions.assertEquals(OperationType.DEPOSIT, statement.get(0).getOperation().getOperationType());
        Assertions.assertEquals(10, statement.get(0).getOperation().getAmount());
        Assertions.assertEquals(10d, statement.get(0).getBalanceAfterOperation());
        Assertions.assertEquals(OperationType.DEPOSIT, statement.get(1).getOperation().getOperationType());
        Assertions.assertEquals(20, statement.get(1).getOperation().getAmount());
        Assertions.assertEquals(30d, statement.get(1).getBalanceAfterOperation());
        Assertions.assertEquals(OperationType.WITHDRAWAL, statement.get(2).getOperation().getOperationType());
        Assertions.assertEquals(5, statement.get(2).getOperation().getAmount());
        Assertions.assertEquals(25d, statement.get(2).getBalanceAfterOperation());
        Assertions.assertEquals(OperationType.WITHDRAWAL, statement.get(3).getOperation().getOperationType());
        Assertions.assertEquals(25, statement.get(3).getOperation().getAmount());
        Assertions.assertEquals(0d, statement.get(3).getBalanceAfterOperation());

        final var afterOperationsTimestamp = LocalDateTime.now();
        Assertions.assertTrue(statement.stream().allMatch(it -> {
            LocalDateTime operationDate = it.getOperation().getDate();
            return operationDate.isAfter(beforeOperationsTimestamp) || operationDate.isEqual(beforeOperationsTimestamp);
        }));
        Assertions.assertTrue(statement.stream().allMatch(it -> {
            LocalDateTime operationDate = it.getOperation().getDate();
            return operationDate.isBefore(afterOperationsTimestamp) || operationDate.isEqual(afterOperationsTimestamp);
        }));
    }
}