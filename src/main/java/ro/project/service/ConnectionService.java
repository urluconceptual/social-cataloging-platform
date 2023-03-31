package ro.project.service;

import ro.project.model.Connection;
import ro.project.model.abstracts.User;

import java.util.Optional;
import java.util.UUID;

public interface ConnectionService {
    Optional<Connection> getConnectionById(UUID id);
    Optional<Connection>  getConnectionByUsers(UUID user1, UUID user2);
    void addConnection(UUID user);

}
