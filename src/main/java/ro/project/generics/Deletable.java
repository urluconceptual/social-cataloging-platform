package ro.project.generics;

import java.util.UUID;

public interface Deletable<T> {
    void deleteById(UUID id);
}
