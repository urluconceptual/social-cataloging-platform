package ro.project.application;

import ro.project.model.*;
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
    private static BookService bookService = new BookServiceImpl();
    private static ConnectionService connectionService = new ConnectionServiceImpl();
    public static ReviewService reviewService = new ReviewServiceImpl();
    private static ReadingChallengeService readingChallengeService = new ReadingChallengeServiceImpl();
    private static GeneralMenu generalMenu = GeneralMenu.getInstance();
    private ShelfService shelfService = new ShelfServiceImpl();

    private ReaderMenu() {
    }

    public static ReaderMenu getInstance() {
        return (INSTANCE == null ? new ReaderMenu() : INSTANCE);
    }

    private static void myConnections() {
        System.out.println("""
                                   
                                   0 -> Go back                                 
                                   1 -> Users you follow
                                   2 -> Users following you
                                   3 -> Friends
                                                                      
                                   Choose option:""");
        String option;
        boolean flag = true;
        do {
            option = scanner.next();
            switch (option) {
                case "0" -> {
                    return;
                }
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
                    List<User> friends = readerService.getFriends().stream().toList();
                    System.out.println("Friends:");
                    friends.forEach(friend -> System.out.println(friend.getUsername()));
                    flag = false;
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
                userService.printUserData(user.get().getId());
                break;
            }
        } while (true);

        if (connectionService.getByUsers(userService.getIdOfCurrentUser(), user.get().getId()).isPresent()
                || connectionService.getByUsers(user.get().getId(), userService.getIdOfCurrentUser()).isPresent()) {
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
                                   
                                   0 -> Go back
                                   1 -> Choose profile to view
                                                                      
                                   Choose option:""");

        String option;
        boolean flag = true;
        do {
            option = scanner.next();
            switch (option) {
                case "0" -> {
                    return;
                }
                case "1" -> {
                    viewProfile();
                    flag = false;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);
    }

    private void addPersonalShelf(String shelfName) {
        shelfService.addShelf(PersonalShelf.builder()
                                           .name(shelfName)
                                           .type(ShelfType.PERSONAL)
                                           .owner(userService.getIdOfCurrentUser())
                                           .build());
    }

    private void addSharedShelf(String shelfName) {
        List<User> friends = readerService.getFriends().stream().toList();
        List<UUID> collaborators = new LinkedList<>();
        collaborators.add(userService.getIdOfCurrentUser());
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

        while (!options.equals("done")) {
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
        System.out.println("Successfully added shelf!");
    }

    private void removeShelf() {
        List<UUID> shelves = ((Reader) userService.getCurrentUser().get()).getShelves();
        System.out.println("Enter index of shelf you want to remove:");
        int i = scanner.nextInt();
        while (i > shelves.size()) {
            generalMenu.invalidMessage("Shelf does not exist.");
            i = scanner.nextInt();
        }
        readerService.removeShelf(shelves.get(i - 1));
        System.out.println("Successfully removed shelf!");
    }

    private void addReview(UUID bookId) {
        scanner.nextLine();
        System.out.println("review: ");
        String text = scanner.nextLine();
        System.out.println("rating(1-10): ");
        int rating = scanner.nextInt();
        while (rating>10 || rating<1) {
            generalMenu.invalidMessage("Please enter a number from specified range.");
            rating = scanner.nextInt();
        }
        Review review = Review.builder()
                .bookId(bookId)
                .readerId(userService.getIdOfCurrentUser())
                .reviewMessage(text)
                .rating(rating)
                .build();
        reviewService.addReview(bookId, review);
    }

    private void addToShelf(UUID shelfId) {
        List<Book> bookList = bookService.getListOfAllBooks();
        showAllBooks();
        System.out.println("Enter index of book you want to add:");
        int input = scanner.nextInt();
        while (input > bookList.size()) {
            generalMenu.invalidMessage("Book index does not exist.");
            input = scanner.nextInt();
        }
        shelfService.addBookToShelf(shelfId, bookList.get(input - 1).getId());
        if (shelfService.getById(shelfId).get().getName().equals("read")) {
            System.out.println("""
                                       
                                       0 -> Go back
                                       1 -> Add review""");
            String option;
            boolean flag = true;
            do {
                option = scanner.next();
                switch (option) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> {
                        addReview(bookList.get(input-1).getId());
                        flag = false;
                    }
                    default -> generalMenu.invalidMessage("Invalid option.");
                }
            } while (flag);
        }
    }

    void removeFromShelf(UUID shelfId) {
        List<UUID> bookList = shelfService.getShelfBooks(shelfId);
        System.out.println("Enter index of book you want to remove:");
        int input = scanner.nextInt();
        while (input > bookList.size()) {
            generalMenu.invalidMessage("Book index does not exist.");
            input = scanner.nextInt();
        }
        shelfService.removeBookFromShelf(shelfId, bookList.get(input - 1));
        System.out.println("Successfully removed book from shelf!");
    }

    private void seeShelf() {
        List<UUID> shelves = ((Reader) userService.getCurrentUser().get()).getShelves();
        System.out.println("Enter index of shelf you want to see:");
        int i = scanner.nextInt();
        while (i > shelves.size()) {
            generalMenu.invalidMessage("Shelf does not exist.");
            i = scanner.nextInt();
        }
        System.out.println("Books on your shelf: ");
        shelfService.printShelfData(shelves.get(i - 1));

        System.out.println("""
                                        
                                   0 -> Go back                              
                                   1 -> Add book to shelf
                                   2 -> Remove book from shelf
                                   
                                                                      
                                   Choose option:""");
        String option;
        boolean flag = true;
        do {
            option = scanner.next();
            switch (option) {
                case "0" -> {
                    return;
                }
                case "1" -> {
                    addToShelf(shelves.get(i - 1));
                    flag = false;
                }
                case "2" -> {
                    removeFromShelf(shelves.get(i - 1));
                    flag = false;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);
    }

    private void myShelves() {
        Reader reader = (Reader) userService.getCurrentUser().get();

        System.out.println("Your current shelves: ");

        readerService.printShelves(reader);
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
                    removeShelf();
                    flag = false;
                }
                case "3" -> {
                    seeShelf();
                    flag = false;
                }
                case "4" -> {
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);

    }

    private void setReadingChallenge() {
        System.out.println("How many books are you challenging yourself to read?");
        int n = scanner.nextInt();
        readingChallengeService.setNewChallenge(n);
    }

    private void myReadingChallenge() {
        readingChallengeService.printStatus();

        System.out.println("""
                                   0 -> Go back
                                   1 -> Set new reading challenge
                                                                      
                                   Choose option: """);

        String options;

        boolean flag = true;
        do {
            options = scanner.next();
            switch (options) {
                case "0" -> {
                    return;
                }
                case "1" -> {
                    setReadingChallenge();
                    flag = false;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);

        System.out.println("Successfully began new reading challenge!");
        readingChallengeService.printStatus();
    }

    private void showAllBooks() {
        List<Book> bookList = bookService.getListOfAllBooks();
        int i = 1;
        for (Book book : bookList) {
            System.out.println("---- " + i + ": ");
            bookService.printBookData(book.getId());
            i++;
        }
    }

    public void start() {
        shelfService = new ShelfServiceImpl();
        System.out.println("""
                                                                      
                                   0 -> Log out
                                   1 -> My shelves
                                        -> Add new shelf
                                        -> Remove shelf
                                        -> See shelf
                                            -> Add book to shelf
                                                -> Leave review
                                            -> Remove book from shelf
                                   2 -> My connections
                                        -> Users you follow
                                        -> Users following you
                                        -> Friends
                                   3 -> Show other readers
                                        -> Choose profile to view
                                            -> Follow user
                                            -> Unfollow user
                                   4 -> Show authors
                                        -> Choose profile to view
                                            -> Follow user
                                            -> Unfollow user
                                   5 -> Show librarians
                                        -> Choose profile to view
                                            -> Follow user
                                            -> Unfollow user
                                   6 -> My reading challenge
                                        -> Set new reading challenge
                                   7 -> Show all books
                                                    
                                   Choose option:""");
        String options;

        boolean flag = true;
        do {
            options = scanner.next();
            switch (options) {
                case "0" -> {
                    return;
                }
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
                    myReadingChallenge();
                    flag = false;
                }
                case "7" -> {
                    showAllBooks();
                    flag = false;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);
        start();
    }
}