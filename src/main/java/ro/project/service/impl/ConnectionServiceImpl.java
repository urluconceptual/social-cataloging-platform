package ro.project.service.impl;

import ro.project.model.Connection;
import ro.project.service.ConnectionService;
import ro.project.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ConnectionServiceImpl implements ConnectionService {
    List<Connection> connections = new ArrayList<>();

    private static UserService userService = new UserServiceImpl();


    @Override
    public Optional<Connection> getById(UUID id) {
        return connections.stream()
                .filter(connection -> connection.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Connection> getByUsers(UUID user1, UUID user2) {
        return connections.stream()
                .filter(connection -> connection.getFollower().equals(user1) && connection.getFollowed().equals(user2))
                .findFirst();
    }

    @Override
    public void addConnection(UUID user) {
        Optional<Connection> connection = getByUsers(userService.getCurrentUser().get().getId(), user);
        if (connection.isEmpty()) {
            Connection newConnection = Connection.builder()
                                                 .follower(userService.getCurrentUser().get().getId())
                                                 .followed(user)
                                                 .build();
            connections.add(newConnection);
            userService.addConnectionId(userService.getCurrentUser().get(), newConnection.getId());
            userService.addConnectionId(userService.getById(user).get(), newConnection.getId());
        }
    }

    @Override
    public void editById(UUID id, Connection newConnection) {
        if (getById(id).isPresent()) {
            connections.remove(getById(id));
            connections.add(newConnection);
        }
    }

    @Override
    public void removeById(UUID id) {
        if (getById(id).isPresent()) {
            connections.remove(getById(id));
        }
    }

    @Override
    public void unfollowConnection(UUID user) {
        Optional<Connection> connection = getByUsers(userService.getCurrentUser().get().getId(), user);
        if (connection.isPresent()) {
            userService.removeConnectionId(user, connection.get().getId());
        }
    }

}
