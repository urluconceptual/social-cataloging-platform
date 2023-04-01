package ro.project.application;

import ro.project.model.Author;
import ro.project.model.PersonalShelf;
import ro.project.model.Reader;
import ro.project.model.SharedShelf;
import ro.project.model.abstracts.User;
import ro.project.model.enums.ShelfType;
import ro.project.model.enums.UserType;
import ro.project.service.*;
import ro.project.service.impl.*;

import java.util.*;

public class AuthorMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static AuthorMenu INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static LibrarianService librarianService = new LibrarianServiceImpl();
    private static ConnectionService connectionService = new ConnectionServiceImpl();
    private static GeneralMenu generalMenu = GeneralMenu.getInstance();
    private ShelfService shelfService = new ShelfServiceImpl();

    private AuthorMenu() {
    }

    public static AuthorMenu getInstance() {
        return (INSTANCE == null ? new AuthorMenu() : INSTANCE);
    }

    private static void myConnections() {
        System.out.println("""
                                                                      
                                   1 -> Users you follow
                                   2 -> Users following you
                                   3 -> Go back
                                                                      
                                   Choose option:""");
        String option;
        boolean flag = true;
        do {
            option = scanner.next();
            switch (option) {
                case "1" -> {
                    System.out.println("You follow:");
                    userService.getFollowing()
                               .forEach(user -> System.out.println(user.getUsername() + " (" + user.getType().getType() + " user)"));
                    flag = false;
                }
                case "2" -> {
                    System.out.println("Followers:");
                    userService.getFollowed()
                               .forEach(user -> System.out.println(user.getUsername() + " (" + user.getType().getType() + " user)"));
                    flag = false;
                }
                case "3" -> {
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);
    }

    private static void viewProfile() {
        System.out.println("Enter a username from the list to view profile!");
        String username;
        Optional<User> user;
        do {
            username = scanner.next();
            user = userService.getByUsername(username);
            if (user.isEmpty()) {
                generalMenu.invalidMessage("Invalid username.");
            } else {
                userService.printUserData(user.get());
                break;
            }
        } while (true);

        if (connectionService.getByUsers(userService.getCurrentUser().get().getId(), user.get().getId()).isPresent()
                || connectionService.getByUsers(user.get().getId(), userService.getCurrentUser().get().getId()).isPresent()) {
            System.out.println("You follow this user!");
        } else {
            System.out.println("You don't follow this user!");
        }

        System.out.println("""
                                                                      
                                   1 -> Follow user
                                   2 -> Unfollow user
                                   3 -> Go back
                                                                      
                                   Choose option:""");

        String option;
        do {
            option = scanner.next();
            switch (option) {
                case "1" -> {
                    connectionService.addConnection(user.get().getId());
                    System.out.println("You follow this user!");
                    return;
                }
                case "2" -> {
                    connectionService.unfollowConnection(user.get().getId());
                    System.out.println("You don't follow this user!");
                    return;
                }
                case "3" -> {
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (true);

    }

    private static void showUsers(UserType type) {
        String userType = type.getType() + "s";
        System.out.printf("These are all the registered %s:%n", userType);
        userService.getByType(type)
                   .stream()
                   .filter(user -> !user.equals(userService.getCurrentUser().get()))
                   .forEach(user -> System.out.println(user.getUsername() + " (" + user.getType().getType() + " user)"
                                                      ));

        System.out.println("""
                                   1 -> Choose profile to view
                                   2 -> Go back
                                                                      
                                   Choose option:""");

        String option;
        boolean flag = true;
        do {
            option = scanner.next();
            switch (option) {
                case "1" -> {
                    viewProfile();
                    flag = false;
                }
                case "2" -> {
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);
    }

    public void myBooks() {
        Author author = (Author) userService.getCurrentUser().get();

        System.out.println("Books you wrote: ");

        authorService.printBooks((Author) userService.getCurrentUser().get());
        System.out.println("""
                                                                      
                                   1 -> Add new book
                                   2 -> Remove book
                                   3 -> Go back
                                                                      
                                   Choose option:""");
        String option;
        boolean flag = true;
        do {
            option = scanner.next();
            switch (option) {
                case "1" -> {
                    flag = false;
                }
                case "2" -> {
                    flag = false;
                }
                case "3" -> {
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);

    }

    public void start() {
        shelfService = new ShelfServiceImpl();
        System.out.println("""
                                                                      
                                   1 -> My books
                                   2 -> My followers
                                   3 -> My influences
                                   4 -> My average rating
                                   5 -> Logout
                                                                      
                                   Choose option:""");
        String options;

        boolean flag = true;
        do {
            options = scanner.next();
            switch (options) {
                case "1" -> {
                    myBooks();
                    flag = false;
                }
                case "2" -> {
                    //myFollowers();
                    flag = false;
                }
                case "3" -> {
                    //myInfluences();
                    flag = false;
                }
                case "4" -> {
                    //myAverageRating();
                    flag = false;
                }
                case "5" -> {
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);
        start();
    }
}