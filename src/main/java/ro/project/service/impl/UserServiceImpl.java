package ro.project.service.impl;

import ro.project.model.Author;
import ro.project.model.Librarian;
import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;
import ro.project.repository.UserRepository;
import ro.project.repository.impl.UserRepositoryImpl;
import ro.project.service.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static final UserRepository userRepository = new UserRepositoryImpl();
    private static Optional<UUID> currentUser = Optional.empty();
    private static ConnectionService connectionService = new ConnectionServiceImpl();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static LibrarianService librarianService = new LibrarianServiceImpl();

    @Override
    public Optional<User> getById(UUID id) {
        return userRepository.getById(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Set<User> getByType(UserType type) {
        return userRepository.getByType(type);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.add(user);
    }

    @Override
    public void addUsers(List<User> userList) {
        userRepository.addAll(userList);
    }

    @Override
    public void editUserById(UUID id, User newUser) {
        if (getById(id).isPresent()) {
            userRepository.updateById(id, newUser);
        }
    }

    @Override
    public void removeUserById(UUID id) {
        if (getById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public void setCurrentUser(String username) {
        if (!username.isEmpty()) {
            currentUser = Optional.of(getByUsername(username).get().getId());
        } else {
            currentUser = Optional.empty();
        }
    }

    @Override
    public Optional<User> getCurrentUser() {
        return userRepository.getById(currentUser.get());
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
        if (user instanceof Librarian librarian) {
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
        getCurrentUser().get().getConnectionIdList().remove(connection);
    }

    @Override
    public Set<User> getFollowing() {
        return connectionService.getFollowing(getCurrentUser().get().getConnectionIdList())
                                .stream()
                                .map(id -> getById(id).get())
                                .collect(Collectors.toSet());
    }

    @Override
    public Set<User> getFollowed() {
        return connectionService.getFollowed(getCurrentUser().get().getConnectionIdList())
                                .stream()
                                .map(id -> getById(id).get())
                                .collect(Collectors.toSet());
    }

}
