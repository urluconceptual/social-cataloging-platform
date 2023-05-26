package ro.project.application;

import ro.project.exceptions.*;
import ro.project.model.Author;
import ro.project.model.Librarian;
import ro.project.model.Reader;
import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;
import ro.project.service.AuthorService;
import ro.project.service.LibrarianService;
import ro.project.service.ReaderService;
import ro.project.service.UserService;
import ro.project.service.impl.AuthorServiceImpl;
import ro.project.service.impl.LibrarianServiceImpl;
import ro.project.service.impl.ReaderServiceImpl;
import ro.project.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GeneralMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static GeneralMenu INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static ReaderMenu readerMenu = ReaderMenu.getInstance();
    private static AuthorMenu authorMenu = AuthorMenu.getInstance();
    private static LibrarianMenu librarianMenu = LibrarianMenu.getInstance();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static LibrarianService librarianService = new LibrarianServiceImpl();

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
            try {
                System.out.println("Choose option: ");
                inputType = scanner.next();
                type = UserType.getEnumByFieldString(inputType);
                if (UserType.NONE.equals(type)) {
                    throw new OptionException();
                }
                break;
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("username: ");
                username = scanner.next();
                if (userService.getByUsername(username).isPresent()) {
                    throw new UsernameInUseException();
                }
                break;
            } catch (UsernameException e) {
                System.out.println(e.getMessage());
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
            try {
                System.out.println("date of birth(yyyy/mm/dd): ");
                inputBirthDate = scanner.next();
                if (!inputBirthDate.matches("[1-2][0-9][0-9][0-9]/[0-1][0-9]/[0-9][0-9]")) {
                    throw new InvalidDate();
                }
                break;
            } catch (InvalidDate e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        LocalDate birthDate = LocalDate.parse(inputBirthDate, format);

        System.out.println("bio: ");
        scanner.nextLine();
        String bio = scanner.nextLine();

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
            case LIBRARIAN -> {
                userService.addUser(Librarian.builder()
                                             .username(username)
                                             .password(password)
                                             .firstName(firstName)
                                             .lastName(lastName)
                                             .birthDate(birthDate)
                                             .bio(bio)
                                             .type(type)
                                             .build());
                librarianService.init((Librarian) userService.getByUsername(username).get());
            }
            case READER -> {
                userService.addUser(Reader.builder()
                                          .username(username)
                                          .password(password)
                                          .firstName(firstName)
                                          .lastName(lastName)
                                          .birthDate(birthDate)
                                          .bio(bio)
                                          .type(type)
                                          .build());
                readerService.init((Reader) userService.getByUsername(username).get());
            }
        }

        userService.setCurrentUser(username);

        System.out.println("""
                                   Account created!
                                   Your accout information:
                                   """);
        System.out.println(userService.getByUsername(username).get());
    }

    private static void login() {
        try {
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
                    throw new UsernameNotRegistered();
                } else {
                    break;
                }
            } while (true);


            do {
                System.out.println("password: ");
                password = scanner.next();
                if (!password.equals(userService.getByUsername(username).get().getPassword())) {
                    throw new PasswordException();
                } else {
                    break;
                }
            } while (true);

            userService.setCurrentUser(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            login();
        }

        System.out.println("Successfully logged in!");
    }

    private static void intro() {
        try {
            System.out.println("""
                                                                              
                                       0 -> Exit
                                       1 -> Register
                                       2 -> Login
                                                                          
                                       Choose option:""");
            String option = scanner.next();
            switch (option) {
                case "0" -> System.exit(0);
                case "1" -> register();
                case "2" -> login();
                default -> {
                    throw new OptionException();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            intro();
        }
    }

    private static void logout() {
        userService.setCurrentUser("");
        System.out.println("""
                                   You logged out! Type anything to restart the application or
                                   type "exit" if you want to close the application.""");
    }

    private static void statistics() {
        Map<UserType, Long> groupByType = userService.getAllUsers()
                                                     .stream()
                                                     .collect(Collectors.groupingBy(User::getType,
                                                                                    Collectors.counting()));
        System.out.println("We currently have " + userService.getAllUsers().size() + " registered users: ");
        groupByType.entrySet().forEach(entry -> System.out.println(entry.getValue() + " " + entry.getKey().getType() + "s"));
        System.out.println();
    }


    public static void start() {
        System.out.println("""
                                   ---- SOCIAL CATALOGING PLATFORM ------------------------------
                                   Welcome! To use the platform, you have to register or to log
                                   into your account.
                                   """);
        statistics();

        intro();

        switch (userService.getCurrentUser().get().getType()) {
            case AUTHOR -> authorMenu.start();
            case LIBRARIAN -> librarianMenu.start();
            case READER -> readerMenu.start();
        }

        logout();
    }
}