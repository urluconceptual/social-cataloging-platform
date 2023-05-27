package ro.project.application;

import ro.project.application.csv.CsvWriter;
import ro.project.exceptions.OptionException;
import ro.project.exceptions.UsernameNotRegistered;
import ro.project.model.*;
import ro.project.model.abstracts.User;
import ro.project.model.enums.ShelfType;
import ro.project.model.enums.UserType;
import ro.project.service.*;
import ro.project.service.impl.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

public class ReaderMenu extends TemplateMenu {
    private static final Scanner scanner = new Scanner(System.in);
    public static ReviewService reviewService = new ReviewServiceImpl();
    private static ReaderMenu INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static BookService bookService = new BookServiceImpl();
    private static ConnectionService connectionService = new ConnectionServiceImpl();
    private static ReadingChallengeService readingChallengeService = new ReadingChallengeServiceImpl();
    private static GeneralMenu generalMenu = GeneralMenu.getInstance();
    private ShelfService shelfService = new ShelfServiceImpl();



    private ReaderMenu() {
    }

    public static ReaderMenu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReaderMenu();
        }
        return INSTANCE;
    }

    private static void myConnections() {
        System.out.println("""
                                                                      
                                   0 -> Go back                                 
                                   1 -> Users you follow
                                   2 -> Users following you
                                   3 -> Friends
                                                                      
                                   Choose option:""");
        String option;
        try {
            option = scanner.next();
            switch (option) {
                case "0" -> {
                    return;
                }
                case "1" -> {
                    System.out.println("You follow:");
                    userService.getFollowing()
                               .forEach(user -> System.out.println(user.getUsername() + " (" + user.getType().getType() + " user)"));
                }
                case "2" -> {
                    System.out.println("Followers:");
                    userService.getFollowed()
                               .forEach(user -> System.out.println(user.getUsername() + " (" + user.getType().getType() + " user)"));
                }
                case "3" -> {
                    List<User> friends = readerService.getFriends().stream().toList();
                    System.out.println("Friends:");
                    friends.forEach(friend -> System.out.println(friend.getUsername()));
                }
                default -> throw new OptionException();
            }
        } catch (OptionException e) {
            System.out.println(e.getMessage());
            myConnections();
        }
    }

    private static void viewProfile() {
        System.out.println("Enter a username from the list to view profile!");
        String username;
        Optional<User> user;
        do {
            try {
                username = scanner.next();
                user = userService.getByUsername(username);
                if (user.isEmpty()) {
                    throw new UsernameNotRegistered();
                }
                userService.printUserData(user.get().getId());
                break;
            } catch (UsernameNotRegistered e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        if (connectionService.getByUsers(userService.getIdOfCurrentUser(), user.get().getId()).isPresent()
                || connectionService.getByUsers(user.get().getId(), userService.getIdOfCurrentUser()).isPresent()) {
            System.out.println("You follow this user!");
        } else {
            System.out.println("You don't follow this user!");
        }

        System.out.println("""
                                    
                                   0 -> Go back                                  
                                   1 -> Follow user
                                   2 -> Unfollow user
                                                                      
                                   Choose option:""");

        String option;
        do {
            try {
                option = scanner.next();
                switch (option) {
                    case "0" -> {
                        return;
                    }
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
                    default -> throw new OptionException();
                }
            } catch (OptionException e) {
                System.out.println(e.getMessage());
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
        do {
            try {
                option = scanner.next();
                switch (option) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> viewProfile();
                    default -> throw new OptionException();
                }
                break;
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
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
        do {
            try {
                option = scanner.next();
                switch (option) {
                    case "1" -> addPersonalShelf(shelfName);
                    case "2" -> addSharedShelf(shelfName);
                    default -> throw new OptionException();
                }
                break;
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        System.out.println("Successfully added shelf!");
    }

    private void removeShelf() {
        List<UUID> shelves = ((Reader) userService.getCurrentUser().get()).getShelves();
        System.out.println("Enter index of shelf you want to remove:");
        try {
            int i = scanner.nextInt();
            if (i > shelves.size()) {
                throw new OptionException();
            }
            readerService.removeShelf(shelves.get(i - 1));
            System.out.println("Successfully removed shelf!");
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            removeShelf();
        } catch (OptionException e) {
            System.out.println(e.getMessage());
            removeShelf();
        }
    }

    private void addReview(UUID bookId) {
        scanner.nextLine();
        System.out.println("review: ");
        String text = scanner.nextLine();
        System.out.println("rating(1-10): ");
        do {
            try {
                String input = scanner.next();
                int rating = Integer.parseInt(input);
                if (rating > 10 || rating < 1) {
                    throw new OptionException();
                }
                Review review = Review.builder()
                                      .bookId(bookId)
                                      .readerId(userService.getIdOfCurrentUser())
                                      .text(text)
                                      .rating(rating)
                                      .build();
                reviewService.addReview(bookId, review);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private void addToShelf(UUID shelfId) {
        List<Book> bookList = bookService.getListOfAllBooks();
        showAllBooks();
        System.out.println("Enter index of book you want to add:");
        String input;
        int n;
        do {
            try {
                input = scanner.next();
                n = Integer.parseInt(input);
                if (n > bookList.size()) {
                    throw new OptionException();
                }
                shelfService.addBookToShelf(shelfId, bookList.get(n - 1).getId());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        System.out.println("Successfully added book to shelf!");

        if (shelfService.getById(shelfId).get().getName().equals("read")) {
            System.out.println("""
                                                                              
                                       0 -> Go back
                                       1 -> Add review""");
            String option;

            do {
                try {
                    option = scanner.next();
                    switch (option) {
                        case "0" -> {
                            return;
                        }
                        case "1" -> addReview(bookList.get(n - 1).getId());
                        default -> throw new OptionException();
                    }
                    break;
                } catch (OptionException e) {
                    System.out.println(e.getMessage());
                }
            } while (true);
        }
    }

    void removeFromShelf(UUID shelfId) {
        List<UUID> bookList = shelfService.getShelfBooks(shelfId);
        System.out.println("Enter index of book you want to remove:");
        String input;
        int n;
        do {
            try {
                input = scanner.next();
                n = Integer.parseInt(input);
                if (n > bookList.size()) {
                    throw new OptionException();
                }
                shelfService.removeBookFromShelf(shelfId, bookList.get(n - 1));
                System.out.println("Successfully removed book from shelf!");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private void seeShelf() {
        List<UUID> shelves = ((Reader) userService.getCurrentUser().get()).getShelves();
        System.out.println("Enter index of shelf you want to see:");
        String input;
        int i;
        do {
            try {
                input = scanner.next();
                i = Integer.parseInt(input);
                if (i > shelves.size()) {
                    throw new OptionException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        System.out.println("Books on your shelf: ");
        shelfService.printShelfData(shelves.get(i - 1));

        System.out.println("""
                                        
                                   0 -> Go back                              
                                   1 -> Add book to shelf
                                   2 -> Remove book from shelf
                                                                      
                                                                      
                                   Choose option:""");
        String option;
        do {
            try {
                option = scanner.next();
                switch (option) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> addToShelf(shelves.get(i - 1));
                    case "2" -> removeFromShelf(shelves.get(i - 1));
                    default -> throw new OptionException();
                }
                break;
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private void myShelves() {
        Reader reader = (Reader) userService.getCurrentUser().get();

        System.out.println("Your current shelves: ");

        readerService.printShelves(reader);
        System.out.println("""
                                        
                                   0 -> Go back                              
                                   1 -> Add new shelf
                                   2 -> Remove shelf
                                   3 -> See shelf
                                                                      
                                   Choose option:""");
        String option;
        do {
            try {
                option = scanner.next();
                switch (option) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> addShelf();
                    case "2" -> removeShelf();
                    case "3" -> seeShelf();
                    default -> throw new OptionException();
                }
                break;
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

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
        do {
            try {
                options = scanner.next();
                switch (options) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> setReadingChallenge();
                    default -> throw new OptionException();
                }
                break;
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

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

    private void myTopBooks() {
        System.out.println("Your best ratings: ");
        readerService.printTopReviews();
    }

    @Override
    protected void welcomeMessage() {
    }

    @Override
    protected void showOptions() {
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
                                   8 -> My top books
                                                    
                                   Choose option:""");
    }

    @Override
    protected void getOption() {
        String option;

        try {
            option = scanner.next();
            lastOption = option;
            switch (option) {
                case "0" -> {
                    return;
                }
                case "1" -> myShelves();
                case "2" -> myConnections();
                case "3" -> showUsers(UserType.READER);
                case "4" -> showUsers(UserType.AUTHOR);
                case "5" -> showUsers(UserType.LIBRARIAN);
                case "6" -> myReadingChallenge();
                case "7" -> showAllBooks();
                case "8" -> myTopBooks();
                default -> throw new OptionException();
            }
        } catch (OptionException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void getInfo() {
        info = "Reader " + userService.getCurrentUser().get().getUsername();

        switch (lastOption) {
            case "0" -> info += " logged out.";
            case "1" -> info += " accessed their shelves.";
            case "2" -> info += " accessed their connections.";
            case "3" -> info += " accessed the list of readers.";
            case "4" -> info += " accessed the list of authors.";
            case "5" -> info += " accessed the list of librarians.";
            case "6" -> info += " accessed their reading challenge.";
            case "7" -> info += " accessed the list of all books.";
            case "8" -> info += " accessed their top books.";
        }

    }

    @Override
    public void redirect() {
        menu();
    }
}