package ro.project.application;

import ro.project.model.Author;
import ro.project.model.Librarian;
import ro.project.model.Reader;
import ro.project.model.enums.UserType;
import ro.project.service.ReaderService;
import ro.project.service.UserService;
import ro.project.service.impl.UserServiceImpl;

import javax.print.DocFlavor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class GeneralMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static GeneralMenu INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static ReaderMenu readerMenu = ReaderMenu.getInstance();

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

    public static void addSomeUsers() {
        userService.addUsers(List.of(
                Reader.builder()
                      .username("reader1")
                      .password("reader1")
                      .firstName("John")
                      .lastName("Doe")
                      .birthDate(LocalDate.of(2000, 1, 1))
                      .bio("i am a reader")
                      .type(UserType.READER)
                      .build(),
                Reader.builder()
                      .username("reader2")
                      .password("reader2")
                      .firstName("Stanley")
                      .lastName("Kubrik")
                      .birthDate(LocalDate.of(2002, 1, 1))
                      .bio("i am a reader")
                      .type(UserType.READER)
                      .build(),
                Reader.builder()
                      .username("reader3")
                      .password("reader3")
                      .firstName("John")
                      .lastName("Locke")
                      .birthDate(LocalDate.of(1973, 4, 6))
                      .bio("i am a reader")
                      .type(UserType.READER)
                      .build(),
                Author.builder()
                      .username("author1")
                      .password("author1")
                      .firstName("Stephen")
                      .lastName("King")
                      .birthDate(LocalDate.of(1970, 1, 1))
                      .bio("i am an author")
                      .type(UserType.AUTHOR)
                      .build(),
                Author.builder()
                      .username("author2")
                      .password("author2")
                      .firstName("Mircea")
                      .lastName("Cartarescu")
                      .birthDate(LocalDate.of(1981, 1, 1))
                      .bio("i am an author")
                      .type(UserType.AUTHOR)
                      .build(),
                Author.builder()
                      .username("author3")
                      .password("author3")
                      .firstName("Gabriel")
                      .lastName("Marquez")
                      .birthDate(LocalDate.of(1995, 4, 2))
                      .bio("i am an author")
                      .type(UserType.AUTHOR)
                      .build(),
                Author.builder()
                      .username("author4")
                      .password("author4")
                      .firstName("Rhys")
                      .lastName("Montrose")
                      .birthDate(LocalDate.of(1995, 4, 2))
                      .bio("i am an author")
                      .type(UserType.AUTHOR)
                      .build(),
                Librarian.builder()
                         .username("librarian1")
                         .password("librarian1")
                         .firstName("Joe")
                         .lastName("Goldberg")
                         .birthDate(LocalDate.of(1999, 12, 1))
                         .bio("i am a librarian")
                         .type(UserType.LIBRARIAN)
                         .build(),
                Librarian.builder()
                         .username("librarian2")
                         .password("librarian2")
                         .firstName("Jane")
                         .lastName("Doe")
                         .birthDate(LocalDate.of(1981, 1, 1))
                         .bio("i am a librarian")
                         .type(UserType.LIBRARIAN)
                         .build()));
    }

    public static void populate() {
        addSomeUsers();
//        addSomeConnections();
//        addSomeShelves();
//        addSomeBooks();
//        addSomeEditions();
//        addSomeReviews();
//        addSomeBookClubs();
//        addSomeMessages();
    }

    public static void start() {
        System.out.println("""
                                   ---- SOCIAL CATALOGING PLATFORM ------------------------------
                                   Welcome! To use the platform, you have to register or to log
                                   into your account.
                                   """);
        intro();

        switch (userService.getCurrentUser().get().getType()) {
//            case AUTHOR -> authorMenu();
//            case LIBRARIAN -> librarianMenu();
            case READER -> readerMenu.start();
        }

        logout();
    }
}