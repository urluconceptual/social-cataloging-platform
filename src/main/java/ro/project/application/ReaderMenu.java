package ro.project.application;

import ro.project.model.Author;
import ro.project.model.Librarian;
import ro.project.model.Reader;
import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;
import ro.project.service.*;
import ro.project.service.impl.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class ReaderMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static ReaderMenu INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static LibrarianService librarianService = new LibrarianServiceImpl();
    private static ConnectionService connectionService = new ConnectionServiceImpl();
    private static GeneralMenu generalMenu = GeneralMenu.getInstance();

    private ReaderMenu() {
    }

    public static ReaderMenu getInstance() {
        return (INSTANCE == null ? new ReaderMenu() : INSTANCE);
    }

    private static void myConnections() {

    }

    private static void viewProfile() {
        System.out.println("Enter a username from the list to view profile!");
        String username;
        Optional<User> user;
        do {
            username = scanner.next();
            user = userService.getByUsername(username);
            if(user.isEmpty())
                generalMenu.invalidMessage("Invalid username.");
            else {
                userService.printUserData(user.get());
                break;
            }
        } while (true);

        System.out.println("""
                                   
                                   1 -> Follow user
                                   2 -> Go back
                                   
                                   Choose option:""");

        String option;
        do {
            option = scanner.next();
            switch (option) {
                case "1" -> {
                    connectionService.addConnection(user.get().getId());
                    System.out.println("Successfully followed!");
                    return;
                }
                case "2" -> {
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
                   .forEach(user -> System.out.println(user.getUsername() + " (" + user.getType().getType() + " user)"));

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

    public static void start() {
        System.out.println("""
                                   1 -> My shelves
                                   2 -> My connections
                                   3 -> Show other readers
                                   4 -> Show authors
                                   5 -> Show librarians
                                   6 -> Logout
                                   
                                   Choose option:""");
        String options;

        boolean flag = true;
        do {
            options = scanner.next();
            switch (options) {
                case "2" -> {
                    myConnections();
                    flag = false;
                }
                case "3" -> {
                    showUsers(UserType.READER);
                    flag = false;
                }
                case "4" -> {
                    showUsers(UserType.AUTHOR);
                    flag = false;
                }
                case "5" -> {
                    showUsers(UserType.LIBRARIAN);
                    flag = false;
                }
                case "6" -> {
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while(flag);
        start();
    }
}