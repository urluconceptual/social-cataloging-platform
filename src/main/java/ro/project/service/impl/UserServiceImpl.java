package ro.project.service.impl;

import ro.project.model.abstracts.User;
import ro.project.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private static Set<User> users = new HashSet<>();

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
}
