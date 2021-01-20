package kata.dao;

import kata.model.Operation;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface DatabaseAccess {
    Optional<Operation> getLastOperation();

    Stream<Operation> getAllOperations();

    void addOperation(Operation operation);
}
