package kata.dao;

import kata.model.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class InMemoryDatabase implements DatabaseAccess {

    private final List<Operation> operations = new ArrayList<>();

    @Override
    public Optional<Operation> getLastOperation() {
        return operations.isEmpty() ? Optional.empty() : Optional.of(operations.get(operations.size() - 1));
    }

    @Override
    public Stream<Operation> getAllOperations() {
        return operations.stream();
    }

    @Override
    public void addOperation(Operation operation) {
        operations.add(operation);
    }
}
