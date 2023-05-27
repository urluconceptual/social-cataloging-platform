package ro.project.service.impl;

import ro.project.model.Connection;
import ro.project.repository.ConnectionRepository;
import ro.project.repository.impl.ConnectionRepositoryImpl;
import ro.project.service.ConnectionService;
import ro.project.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConnectionServiceImpl implements ConnectionService {
    private static ConnectionRepository connectionRepository = new ConnectionRepositoryImpl();

    private static UserService userService = new UserServiceImpl();


    @Override
    public Optional<Connection> getById(UUID id) {
        return connectionRepository.getById(id);
    }

    @Override
    public Optional<Connection> getByUsers(UUID user1, UUID user2) {
        List<Connection> connections = connectionRepository.getAll();
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
            connectionRepository.add(newConnection);
            userService.addConnectionId(userService.getCurrentUser().get(), newConnection.getId());
            userService.addConnectionId(userService.getById(user).get(), newConnection.getId());
        }
    }

    @Override
    public void addConnections(List<Connection> connectionList) {
        for (Connection connection : connectionList) {
            connectionRepository.add(connection);
            userService.addConnectionId(userService.getById(connection.getFollower()).get(), connection.getId());
            userService.addConnectionId(userService.getById(connection.getFollowed()).get(), connection.getId());
        }
    }

    @Override
    public void editById(UUID id, Connection newConnection) {
        if (getById(id).isPresent()) {
            connectionRepository.updateById(id, newConnection);
        }
    }

    @Override
    public void removeById(UUID id) {
        if (getById(id).isPresent()) {
            connectionRepository.deleteById(id);
        }
    }

    @Override
    public void unfollowConnection(UUID user) {
        Optional<Connection> connection = getByUsers(userService.getCurrentUser().get().getId(), user);
        if (connection.isPresent()) {
            userService.removeConnectionId(user, connection.get().getId());
        }
    }

    @Override
    public Set<UUID> getFollowing(List<UUID> connectionIdList) {
        return connectionIdList.stream()
                               .map(id -> getById(id).get())
                               .filter(connection -> connection.getFollower().equals(userService.getCurrentUser().get().getId()))
                               .map(Connection::getFollowed)
                               .collect(Collectors.toSet());
    }

    @Override
    public Set<UUID> getFollowed(List<UUID> connectionIdList) {
        return connectionIdList.stream()
                               .map(id -> getById(id).get())
                               .filter(connection -> connection.getFollowed().equals(userService.getCurrentUser().get().getId()))
                               .map(Connection::getFollower)
                               .collect(Collectors.toSet());
    }

}
