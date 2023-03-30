package ro.project.service.impl;

import ro.project.model.abstracts.User;
import ro.project.service.UserService;

import java.util.*;

public class UserServiceImpl implements UserService {
    private static final Set<User> users = new HashSet<>();
    private static Optional<User> currentUser;

    @Override
    public Optional<User> getById(UUID id) {
        return users.stream()
                    .filter(user -> user.getId().equals(id))
                    .findFirst();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return users.stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst();
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void addUsers(List<User> userList) {
        users.addAll(userList);
    }

    @Override
    public void editUserById(UUID id, User newUser) {
        if (getById(id).isPresent()) {
            users.remove(getById(id));
            users.add(newUser);
        }
    }

    @Override
    public void removeUserById(UUID id) {
        if (getById(id).isPresent()) {
            users.remove(getById(id));
        }
    }

    @Override
    public void setCurrentUser(String username) {
        if (!username.isEmpty())
            currentUser = getByUsername(username);
        else
            currentUser = Optional.empty();
    }

    @Override
    public Optional<User> getCurrentUser() {
        return currentUser;
    }
}
