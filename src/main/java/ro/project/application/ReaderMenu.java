package ro.project.application;

import ro.project.model.Author;
import ro.project.model.Librarian;
import ro.project.model.Reader;
import ro.project.model.enums.UserType;
import ro.project.service.UserService;
import ro.project.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ReaderMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static ReaderMenu INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static GeneralMenu generalMenu = GeneralMenu.getInstance();

    private ReaderMenu() {
    }

    public static ReaderMenu getInstance() {
        return (INSTANCE == null ? new ReaderMenu() : INSTANCE);
    }

    private static void myConnections() {

    }

    private static void showUsers(UserType type) {
        String userType = type.getType() + "s";
        System.out.printf("These are all the registered %s:%n", userType);
        userService.getByType(type)
                   .forEach(user -> System.out.println(user.getUsername() + "(" + user.getType().getType() + ")"));
    }

    public static void start() {
        System.out.println("""
                                   1 -> My shelves
                                   2 -> My connections
                                   3 -> Show other readers
                                   4 -> Show authors
                                   5 -> Show librarians""");
        String options = scanner.next();

        switch (options) {
            case "2" -> myConnections();
            case "3" -> showUsers(UserType.READER);
            case "4" -> showUsers(UserType.AUTHOR);
            case "5" -> showUsers(UserType.LIBRARIAN);
            default -> generalMenu.invalidMessage("Invalid option.");
        }
    }
}