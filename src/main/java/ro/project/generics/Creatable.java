package ro.project.generics;

import java.util.List;

public interface Creatable<T> {
    void add(T object);

    void addAll(List<T> objectList);
}
