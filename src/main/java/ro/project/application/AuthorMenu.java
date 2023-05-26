package ro.project.application;

import ro.project.exceptions.OptionException;
import ro.project.model.Author;
import ro.project.model.Book;
import ro.project.model.enums.BookGenre;
import ro.project.service.*;
import ro.project.service.impl.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class AuthorMenu extends TemplateMenu {
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
        System.out.println("book title: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("genre: ");
        for (BookGenre bookGenre : BookGenre.values()) {
            System.out.println("    " + bookGenre.getName());
        }

        String inputType = scanner.next();
        BookGenre genre = BookGenre.getEnumByFieldString(inputType);

        System.out.println("number of pages: ");
        String input;
        int n;
        do {
            try {
                input = scanner.next();
                n = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            }
        } while (true);

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
        Author author = (Author) userService.getCurrentUser().get();
        List<UUID> bookList = authorService.getWrittenBooks(author);
        System.out.println("Enter index of book you want to remove: ");
        String input;
        int n;
        try {
            input = scanner.next();
            n = Integer.parseInt(input);
            if (n > bookList.size()) {
                throw new OptionException();
            }
            bookService.removeBookById(bookList.get(n - 1));
            System.out.println("Successfully removed book!");
        } catch (OptionException e) {
            System.out.println(e.getMessage());
            removeBook();
        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer.");
            removeBook();
        }
    }

    private void myBooks() {
        Author author = (Author) userService.getCurrentUser().get();

        System.out.println("Books you wrote: ");

        authorService.printBooks((Author) userService.getCurrentUser().get());
        System.out.println("""
                                     
                                   0 -> Go back                                 
                                   1 -> Add new book
                                   2 -> Remove book
                                                                      
                                   Choose option:""");
        String option;
        try {
            option = scanner.next();
            switch (option) {
                case "0" -> {
                    return;
                }
                case "1" -> addNewBook();
                case "2" -> removeBook();
                default -> throw new OptionException();
            }
        } catch (OptionException e) {
            System.out.println(e.getMessage());
        } finally {
            myBooks();
        }
    }

    private void myFollowers() {
        userService.getFollowed().forEach(user -> System.out.println(user.getUsername()));
    }

    private void myAverageRating() {
        ((Author) userService.getCurrentUser().get()).getAverageRating();
    }

    @Override
    protected void welcomeMessage() {}

    @Override
    protected void showOptions() {
        System.out.println("""
                                    
                                   0 -> Log out                                  
                                   1 -> My books
                                        -> Add new book
                                        -> Remove book
                                   2 -> My followers
                                   3 -> My average rating
                                                                      
                                   Choose option:""");
    }

    @Override
    protected void getOption() {
        String options;
        try {
            options = scanner.next();
            switch (options) {
                case "0" -> {
                    return;
                }
                case "1" -> myBooks();
                case "2" -> myFollowers();
                case "3" -> myAverageRating();
                default -> throw new OptionException();
            }
        } catch (OptionException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void redirect() {
        menu();
    }
}