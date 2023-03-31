package ro.project.application;

import ro.project.model.PersonalShelf;
import ro.project.model.Reader;
import ro.project.model.SharedShelf;
import ro.project.model.abstracts.User;
import ro.project.model.enums.ShelfType;
import ro.project.model.enums.UserType;
import ro.project.service.*;
import ro.project.service.impl.*;

import java.util.*;

public class ReaderMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static ReaderMenu INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static LibrarianService librarianService = new LibrarianServiceImpl();
    private static ConnectionService connectionService = new ConnectionServiceImpl();
    private static GeneralMenu generalMenu = GeneralMenu.getInstance();
    private ShelfService shelfService = new ShelfServiceImpl();

    private ReaderMenu() {
    }

    public static ReaderMenu getInstance() {
        return (INSTANCE == null ? new ReaderMenu() : INSTANCE);
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

    private void addPersonalShelf(String shelfName) {
        shelfService.addShelf(PersonalShelf.builder()
                                           .name(shelfName)
                                           .type(ShelfType.PERSONAL)
                                           .owner(userService.getCurrentUser().get().getId())
                                           .build());
    }

    private void addSharedShelf(String shelfName) {
        List<User> friends = readerService.getFriends().stream().toList();
        List<UUID> collaborators = new LinkedList<>();
        collaborators.add(userService.getCurrentUser().get().getId());
        System.out.println("Choose friends to collaborate:");
        int i = 0;
        for (; i < friends.size(); i++) {
            System.out.println((i + 1) + " -> " + friends.get(i).getUsername());
        }
        System.out.println("done -> Done");

        System.out.println("""
                                   Choose indexes for all the collaborators you want to add,
                                   or type "done" if you're done:""");

        String options = scanner.next();

        while(!options.equals("done")) {
            collaborators.add(friends.get(Integer.parseInt(options) - 1).getId());
            options = scanner.next();
        }

        shelfService.addShelf(SharedShelf.builder()
                                         .name(shelfName)
                                         .type(ShelfType.SHARED)
                                         .ownerIdList(collaborators)
                                         .build());
    }

    private void addShelf() {
        System.out.println("Enter shelf name (one word):");
        String shelfName = scanner.next();
        System.out.println("""
                                   Type of shelf:
                                   1 -> Personal
                                   2 -> Shared
                                                                      
                                   Choose option:""");
        String option;
        boolean flag = true;
        do {
            option = scanner.next();
            switch (option) {
                case "1" -> {
                    addPersonalShelf(shelfName);
                    flag = false;
                }
                case "2" -> {
                    addSharedShelf(shelfName);
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);
    }

    public void myShelves() {
        System.out.println("Your current shelves: ");
        Reader reader = (Reader) userService.getCurrentUser().get();
        reader.getShelves()
              .forEach(shelf -> System.out.println(shelf.getName()));
        System.out.println("""
                                                                      
                                   1 -> Add new shelf
                                   2 -> Remove shelf
                                   3 -> See shelf
                                   4 -> Go back
                                                                      
                                   Choose option:""");
        String option;
        boolean flag = true;
        do {
            option = scanner.next();
            switch (option) {
                case "1" -> {
                    addShelf();
                    flag = false;
                }
                case "2" -> {
                    flag = false;
                }
                case "3" -> {
                    flag = false;
                }
                case "4" -> {
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);

    }

    public void start() {
        shelfService = new ShelfServiceImpl();
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
                case "1" -> {
                    myShelves();
                    flag = false;
                }
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
        } while (flag);
        start();
    }
}