package ro.project.repository;

import ro.project.generics.Creatable;
import ro.project.generics.Deletable;
import ro.project.generics.Readable;
import ro.project.generics.Updatable;
import ro.project.model.Connection;

import java.util.List;
import java.util.UUID;

public interface ConnectionRepository extends Creatable<Connection>, Readable<Connection>, Updatable<Connection>, Deletable<Connection> {
    public List<Connection> getAllByUserId(UUID userId);
}
