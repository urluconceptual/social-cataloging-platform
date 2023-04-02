package ro.project.service;

import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    Optional<User> getById(UUID id);

    Optional<User> getByUsername(String username);

    Set<User> getByType(UserType type);

    void addUser(User user);

    void addUsers(List<User> userList);

    void editUserById(UUID id, User newUser);

    void removeUserById(UUID id);

    Optional<User> getCurrentUser();
    UUID getIdOfCurrentUser();

    void setCurrentUser(String username);

    void printUserData(UUID userId);

    void addConnectionId(User user, UUID connection);

    void removeConnectionId(UUID user, UUID connection);

    Set<User> getFollowing();

    Set<User> getFollowed();
}
