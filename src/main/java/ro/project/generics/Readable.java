package ro.project.generics;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Readable<T> {
    Optional<T> getById(UUID id);

    List<T> getAll();
}
