package ro.project.service.impl;

import ro.project.model.Connection;
import ro.project.model.abstracts.User;
import ro.project.service.ConnectionService;
import ro.project.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ConnectionServiceImpl implements ConnectionService {
    List<Connection> connectionList = new ArrayList<>();

    private static UserService userService = new UserServiceImpl();


    @Override
    public Optional<Connection> getConnectionById(UUID id) {
        return connectionList.stream()
                .filter(connection -> connection.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Connection> getConnectionByUsers(UUID user1, UUID user2) {
        return connectionList.stream()
                .filter(connection -> connection.getUserId1().equals(user1) && connection.getUserId2().equals(user2))
                .findFirst();
    }

    @Override
    public void addConnection(UUID user) {
        Optional<Connection> connection = getConnectionByUsers(user, userService.getCurrentUser().get().getId());
        if (connection.isPresent()) {
            connection.get().setTwoFollowsOne(true);
            userService.addConnectionId(userService.getCurrentUser().get(), connection.get().getId());
        }
        else {
            Connection newConnection = Connection.builder()
                                                 .userId1(userService.getCurrentUser().get().getId())
                                                 .userId2(user)
                                                 .oneFollowsTwo(true)
                                                 .twoFollowsOne(false)
                                                 .build();
            connectionList.add(newConnection);
            userService.addConnectionId(userService.getCurrentUser().get(), newConnection.getId());
            userService.addConnectionId(userService.getById(user).get(), newConnection.getId());
        }
    }
}
