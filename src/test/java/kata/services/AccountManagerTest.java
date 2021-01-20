package kata.services;

import kata.dao.DatabaseAccess;
import kata.dao.InMemoryDatabase;
import kata.exception.InsufficientFundsException;
import kata.exception.InvalidOperationException;
import kata.model.Operation;
import kata.model.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest
class AccountManagerTest {

    private DatabaseAccess inMemoryDatabaseAccess;

    @MockBean
    private DatabaseAccess databaseAccess;

    @Autowired
    private AccountManager service;

    @BeforeEach
    public void initialize() {
        /*
          Actually the InMemoryDatabase implementation should only exist in this test case
          For this Kata, we will not dive into the actual implementation of the connection to a database etc
         */
        inMemoryDatabaseAccess = new InMemoryDatabase();
        Mockito.when(databaseAccess.getAllOperations()).thenAnswer(invocationOnMock -> inMemoryDatabaseAccess.getAllOperations());
        Mockito.when(databaseAccess.getLastOperation()).thenAnswer(invocationOnMock -> inMemoryDatabaseAccess.getLastOperation());
        Mockito.doAnswer(invocationOnMock -> {
            inMemoryDatabaseAccess.addOperation(invocationOnMock.getArgument(0));
            return Void.TYPE;
        }).when(databaseAccess).addOperation(Mockito.any());
    }

    @Test
    void printStatement_should_return_empty_when_there_are_no_operations() {
        Assertions.assertTrue(service.printStatement().isEmpty());
    }

    @Test()
    void withdraw_should_return_exception_when_there_are_insufficient_funds() {
        Assertions.assertThrows(InsufficientFundsException.class, () -> service.withdraw(1));
    }

    @Test()
    void deposit_and_withdrawal_should_fail_for_negative_amounts() {
        Arrays.asList(0, -1).forEach(amount -> {
            Assertions.assertThrows(InvalidOperationException.class, () -> service.withdraw(amount));
            Assertions.assertThrows(InvalidOperationException.class, () -> service.deposit(amount));
        });
    }

    @Test()
    void withdrawal_with_insufficient_funds_should_throw_exception() {
        Assertions.assertThrows(InsufficientFundsException.class, () -> service.withdraw(10));

        final var initialDeposit = 100d;
        service.deposit(initialDeposit);
        Assertions.assertEquals(initialDeposit, service.getBalance());

        service.withdraw(25d);
        Assertions.assertEquals(75d, service.getBalance());
    }

    @Test()
    void printStatement_should_return_operations_made() {
        final var beforeOperationsTimestamp = LocalDateTime.now();

        service.deposit(10);
        Assertions.assertEquals(10d, service.getBalance());
        service.deposit(20);
        Assertions.assertEquals(30d, service.getBalance());
        service.withdraw(5);
        Assertions.assertEquals(25d, service.getBalance());
        Assertions.assertThrows(InsufficientFundsException.class, () -> service.withdraw(25.01));
        service.withdraw(25);
        Assertions.assertEquals(0d, service.getBalance());

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