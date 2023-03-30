package ro.project.application;

import ro.project.model.Author;
import ro.project.model.Librarian;
import ro.project.model.Reader;
import ro.project.model.enums.UserType;
import ro.project.service.UserService;
import ro.project.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static Menu INSTANCE;
    private static UserService userService = new UserServiceImpl();

    private Menu() {
    }

    public static Menu getInstance() {
        return (INSTANCE == null ? new Menu() : INSTANCE);
    }

    private static void invalidMessage(String invalidItem) {
        StringBuilder message = new StringBuilder("""
                                                          Try again! """);
        message.append(invalidItem);
        System.out.println(message);
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

        userService.addUser(switch (type) {
            case AUTHOR -> Author.builder()
                                 .id(UUID.randomUUID())
                                 .creationDate(LocalDate.now())
                                 .username(username)
                                 .password(password)
                                 .firstName(firstName)
                                 .lastName(lastName)
                                 .birthDate(birthDate)
                                 .bio(bio)
                                 .type(type)
                                 .build();
            case LIBRARIAN -> Librarian.builder()
                                       .id(UUID.randomUUID())
                                       .creationDate(LocalDate.now())
                                       .username(username)
                                       .password(password)
                                       .firstName(firstName)
                                       .lastName(lastName)
                                       .birthDate(birthDate)
                                       .bio(bio)
                                       .type(type)
                                       .build();
            case READER -> Reader.builder()
                                 .id(UUID.randomUUID())
                                 .creationDate(LocalDate.now())
                                 .username(username)
                                 .password(password)
                                 .firstName(firstName)
                                 .lastName(lastName)
                                 .birthDate(birthDate)
                                 .bio(bio)
                                 .type(type)
                                 .build();
            default -> null;
        });

        System.out.println("""
                                   Account created!
                                   Your accout information:
                                   """);
        System.out.println(userService.getByUsername(username));
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

        System.out.println("Successfully logged in!");
    }

    private static void intro() {
        System.out.println("""
                                   1 -> Register
                                   2 -> Login
                                                                      
                                   Choose option: """);
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> register();
            case 2 -> login();
            default -> {
                invalidMessage("Invalid option.");
                intro();
            }
        }
    }

    public static void start() {
        System.out.println("""
                                   ---- SOCIAL CATALOGING PLATFORM ------------------------------
                                   Welcome! To use the platform, you have to register or to log
                                   into your account.
                                   """);
        intro();
    }
}