package ro.project.application;

import ro.project.model.*;
import ro.project.model.enums.BookGenre;
import ro.project.model.enums.UserType;
import ro.project.service.*;
import ro.project.service.impl.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GeneralMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static GeneralMenu INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static ReaderMenu readerMenu = ReaderMenu.getInstance();
    private static AuthorMenu authorMenu = AuthorMenu.getInstance();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static BookService bookService = new BookServiceImpl();
    private static ConnectionService connectionService = new ConnectionServiceImpl();

    private GeneralMenu() {
    }

    public static GeneralMenu getInstance() {
        return (INSTANCE == null ? new GeneralMenu() : INSTANCE);
    }

    protected static void invalidMessage(String invalidItem) {
        System.out.println("Try again! " + invalidItem);
    }

    private static void register() {
        System.out.println("""
                                   ---- REGISTER ------------------------------------------------
                                   Complete information to create an account.
                                   """);

        System.out.println("""
                                   What type of account do you want to create?
                                   """);

        for (UserType userType : UserType.values()) {
            if (!UserType.NONE.equals(userType)) {
                System.out.println("    " + userType.getType());
            }
        }

        String inputType;
        String username;
        UserType type;

        do {
            System.out.println("Choose option: ");
            inputType = scanner.next();
            type = UserType.getEnumByFieldString(inputType);
            if (UserType.NONE.equals(type)) {
                invalidMessage("Invalid option.");
            } else {
                break;
            }
        } while (true);

        do {
            System.out.println("username: ");
            username = scanner.next();
            if (userService.getByUsername(username).isPresent()) {
                invalidMessage("Username already in use.");
            } else {
                break;
            }
        } while (true);

        System.out.println("password: ");
        String password = scanner.next();

        System.out.println("first name: ");
        String firstName = scanner.next();

        System.out.println("last name: ");
        String lastName = scanner.next();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String inputBirthDate;
        do {
            System.out.println("date of birth(yyyy/mm/dd): ");
            inputBirthDate = scanner.next();
            if (!inputBirthDate.matches("[1-2][0-9][0-9][0-9]/[0-1][0-9]/[0-9][0-9]")) {
                invalidMessage("Invalid date.");
            } else {
                break;
            }
        } while (true);
        LocalDate birthDate = LocalDate.parse(inputBirthDate, format);

        System.out.println("bio: ");
        String bio = scanner.next();

        switch (type) {
            case AUTHOR -> {
                userService.addUser(Author.builder()
                                          .username(username)
                                          .password(password)
                                          .firstName(firstName)
                                          .lastName(lastName)
                                          .birthDate(birthDate)
                                          .bio(bio)
                                          .type(type)
                                          .build());
                authorService.init((Author) userService.getByUsername(username).get());
            }
            case LIBRARIAN -> userService.addUser(Librarian.builder()
                                       .username(username)
                                       .password(password)
                                       .firstName(firstName)
                                       .lastName(lastName)
                                       .birthDate(birthDate)
                                       .bio(bio)
                                       .type(type)
                                       .build());
            case READER -> {userService.addUser(Reader.builder()
                                 .username(username)
                                 .password(password)
                                 .firstName(firstName)
                                 .lastName(lastName)
                                 .birthDate(birthDate)
                                 .bio(bio)
                                 .type(type)
                                 .build());
                readerService.init((Reader)userService.getByUsername(username).get());
            }
            default -> {}
        };

        userService.setCurrentUser(username);

        System.out.println("""
                                   Account created!
                                   Your accout information:
                                   """);
        System.out.println(userService.getByUsername(username).get());
    }

    private static void login() {
        System.out.println("""
                                   ---- LOGIN ---------------------------------------------------
                                   Please enter your username and password to login.
                                   """);
        String username;
        String password;
        do {
            System.out.println("username: ");
            username = scanner.next();
            if (userService.getByUsername(username).isEmpty()) {
                invalidMessage("Invalid username.");
            } else {
                break;
            }
        } while (true);

        do {
            System.out.println("password: ");
            password = scanner.next();
            if (!password.equals(userService.getByUsername(username).get().getPassword())) {
                invalidMessage("Invalid password.");
            } else {
                break;
            }
        } while (true);

        userService.setCurrentUser(username);

        System.out.println("Successfully logged in!");
    }

    private static void intro() {
        System.out.println("""
                                   1 -> Register
                                   2 -> Login
                                   3 -> Exit
                                                                      
                                   Choose option:""");
        String option = scanner.next();
        switch (option) {
            case "1" -> register();
            case "2" -> login();
            case "3" -> System.exit(0);
            default -> {
                invalidMessage("Invalid option.");
                intro();
            }
        }
    }

    private static void logout() {
        userService.setCurrentUser("");
        System.out.println("""
                                   You logged out! Type anything to restart the application or
                                   type "exit" if you want to close the application.""");
    }



    public static void start() {
        System.out.println("""
                                   ---- SOCIAL CATALOGING PLATFORM ------------------------------
                                   Welcome! To use the platform, you have to register or to log
                                   into your account.
                                   """);
        intro();

        switch (userService.getCurrentUser().get().getType()) {
            case AUTHOR -> authorMenu.start();
            //case LIBRARIAN -> librarianMenu();
            case READER -> readerMenu.start();
        }

        logout();
    }
}