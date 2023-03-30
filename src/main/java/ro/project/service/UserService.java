package ro.project.service;

import ro.project.model.abstracts.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getById(UUID id);

    Optional<User> getByUsername(String username);

    void addUser(User user);

    void addUsers(List<User> userList);

    void editUserById(UUID id, User newUser);

    void removeUserById(UUID id);
    void setCurrentUser(String username);
    Optional<User> getCurrentUser();
}
