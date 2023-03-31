package ro.project.service;

import ro.project.model.Connection;
import ro.project.model.abstracts.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ConnectionService {
    Optional<Connection> getById(UUID id);
    Optional<Connection> getByUsers(UUID user1, UUID user2);
    void addConnection(UUID user);
    void addConnections(List<Connection> connectionList);
    void editById(UUID id, Connection newConnection);
    void removeById(UUID id);
    void unfollowConnection(UUID user);

    Set<UUID> getFollowing(List<UUID> connectionIdList);

    Set<UUID> getFollowed(List<UUID> connectionIdList);

}
