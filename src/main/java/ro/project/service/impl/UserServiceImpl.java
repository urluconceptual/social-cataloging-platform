package ro.project.service.impl;

import ro.project.model.Author;
import ro.project.model.Librarian;
import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;
import ro.project.service.*;

import java.util.*;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static final Set<User> users = new HashSet<>();
    private static Optional<User> currentUser = Optional.empty();
    private static ConnectionService connectionService = new ConnectionServiceImpl();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static LibrarianService librarianService = new LibrarianServiceImpl();

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
    public Set<User> getByType(UserType type) {
        return users.stream()
                    .filter(user -> user.getType().equals(type))
                    .collect(Collectors.toSet());
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
        if (!username.isEmpty()) {
            currentUser = getByUsername(username);
        } else {
            currentUser = Optional.empty();
        }
    }

    @Override
    public Optional<User> getCurrentUser() {
        return currentUser;
    }

    @Override
    public UUID getIdOfCurrentUser() {
        return getCurrentUser().get().getId();
    }

    @Override
    public void printUserData(UUID userId) {
        User user = getById(userId).get();
        System.out.println("username: " + user.getUsername());
        System.out.println("name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("date of birth: " + user.getBirthDate());
        System.out.println("about: " + user.getBio());

        if (user instanceof Author author) {
            authorService.printAuthorData(author);
        }
        if(user instanceof Librarian librarian) {
            librarianService.printLibrarianData(librarian);
        }
    }

    @Override
    public void addConnectionId(User user, UUID connection) {
        user.getConnectionIdList().add(connection);
    }

    @Override
    public void removeConnectionId(UUID user, UUID connection) {
        getById(user).get().getConnectionIdList().remove(connection);
        currentUser.get().getConnectionIdList().remove(connection);
    }

    @Override
    public Set<User> getFollowing() {
        return connectionService.getFollowing(currentUser.get().getConnectionIdList())
                                .stream()
                                .map(id -> getById(id).get())
                                .collect(Collectors.toSet());
    }

    @Override
    public Set<User> getFollowed() {
        return connectionService.getFollowed(currentUser.get().getConnectionIdList())
                                .stream()
                                .map(id -> getById(id).get())
                                .collect(Collectors.toSet());
    }

}
