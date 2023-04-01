package ro.project.application;

import ro.project.model.Author;
import ro.project.model.Book;
import ro.project.model.abstracts.User;
import ro.project.model.enums.BookGenre;
import ro.project.model.enums.UserType;
import ro.project.service.*;
import ro.project.service.impl.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class AuthorMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static AuthorMenu INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static BookService bookService = new BookServiceImpl();
    private static LibrarianService librarianService = new LibrarianServiceImpl();
    private static ConnectionService connectionService = new ConnectionServiceImpl();
    private static GeneralMenu generalMenu = GeneralMenu.getInstance();
    private ShelfService shelfService = new ShelfServiceImpl();

    private AuthorMenu() {
    }

    public static AuthorMenu getInstance() {
        return (INSTANCE == null ? new AuthorMenu() : INSTANCE);
    }

    private void addNewBook() {
        System.out.println("book title(please use this-format-for-your-title): ");
        String title = scanner.next();
        System.out.println("genre: ");
        for (BookGenre bookGenre : BookGenre.values()) {
                System.out.println("    " + bookGenre.getName());
        }

        String inputType = scanner.next();
        BookGenre genre = BookGenre.getEnumByFieldString(inputType);

        System.out.println("number of pages: ");
        int n = scanner.nextInt();

        Author author = (Author) userService.getCurrentUser().get();

        bookService.addBook(Book.builder()
                                    .title(title)
                                    .authorId(Optional.of(author.getId()))
                                    .author(author.getFirstName() + " " + author.getLastName())
                                    .genre(genre)
                                    .numberOfPages(n)
                                    .build());
        bookService.init();
    }

    private void removeBook() {
        Author author = (Author)userService.getCurrentUser().get();
        List<UUID> bookList = authorService.getWrittenBooks(author);
        System.out.println("Enter index of book you want to remove: ");
        int input = scanner.nextInt();
        while (input > bookList.size()) {
            generalMenu.invalidMessage("Book index does not exist.");
            input = scanner.nextInt();
        }
        bookService.removeBookById(bookList.get(input - 1));
        System.out.println("Successfully removed book!");
    }

    private void myBooks() {
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
                    addNewBook();
                    flag = false;
                }
                case "2" -> {
                    removeBook();
                    flag = false;
                }
                case "3" -> {
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);

    }
    private void myFollowers() {
        userService.getFollowed().forEach(user -> System.out.println(user.getUsername()));
    }

    public void start() {
        shelfService = new ShelfServiceImpl();
        System.out.println("""
                                                                      
                                   1 -> My books
                                   2 -> My followers
                                   3 -> My average rating
                                   4 -> Logout
                                                                      
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
                    myFollowers();
                    flag = false;
                }
                case "3" -> {
                    //myAverageRating();
                    flag = false;
                }
                case "4" -> {
                    return;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);
        start();
    }
}