package ro.project.service;

import ro.project.model.Connection;

import java.util.Optional;
import java.util.UUID;

public interface ConnectionService {
    Optional<Connection> getById(UUID id);
    Optional<Connection> getByUsers(UUID user1, UUID user2);
    void addConnection(UUID user);
    void editById(UUID id, Connection newConnection);
    void removeById(UUID id);

    void unfollowConnection(UUID user);
}
