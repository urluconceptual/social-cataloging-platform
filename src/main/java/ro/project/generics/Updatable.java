package ro.project.generics;

import java.util.UUID;

public interface Updatable<T> {
    void updateById(UUID id, T newObject);
}
