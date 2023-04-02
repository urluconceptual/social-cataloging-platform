package ro.project.application;

import ro.project.model.Book;
import ro.project.model.BookClub;
import ro.project.model.Librarian;
import ro.project.model.records.Message;
import ro.project.service.*;
import ro.project.service.impl.*;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class LibrarianMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static LibrarianMenu INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static BookService bookService = new BookServiceImpl();
    private static LibrarianService librarianService = new LibrarianServiceImpl();
    private static ConnectionService connectionService = new ConnectionServiceImpl();
    private static BookClubService bookClubService = new BookClubServiceImpl();
    private static GeneralMenu generalMenu = GeneralMenu.getInstance();
    private ShelfService shelfService = new ShelfServiceImpl();

    private LibrarianMenu() {
    }

    public static LibrarianMenu getInstance() {
        return (INSTANCE == null ? new LibrarianMenu() : INSTANCE);
    }

    private void addNewBook() {
        Librarian librarian = (Librarian) userService.getCurrentUser().get();
        List<Book> bookList = bookService.getListOfAllBooks();
        showAllBooks();
        System.out.println("Enter index of book you want to recommend:");
        int input = scanner.nextInt();
        while (input > bookList.size()) {
            generalMenu.invalidMessage("Book index does not exist.");
            input = scanner.nextInt();
        }
        shelfService.addBookToShelf(librarian.getRecommendationsList(), bookList.get(input - 1).getId());
    }

    private void removeBook() {
        Librarian librarian = (Librarian) userService.getCurrentUser().get();
        List<UUID> bookList = librarianService.getRecommendedBooks(librarian);
        System.out.println("Enter index of book you want to remove: ");
        int input = scanner.nextInt();
        while (input > bookList.size()) {
            generalMenu.invalidMessage("Book index does not exist.");
            input = scanner.nextInt();
        }
        shelfService.removeBookFromShelf(librarian.getRecommendationsList(), bookList.get(input - 1));
        System.out.println("Successfully removed book from shelf!");
    }

    private void myBooks() {
        Librarian librarian = (Librarian) userService.getCurrentUser().get();

        System.out.println("Books you recommend: ");
        librarianService.printBooks((Librarian) userService.getCurrentUser().get());
        System.out.println("""
                                       
                                   0 -> Go back                               
                                   1 -> Add new book
                                   2 -> Remove book
                                                                      
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
                    addNewBook();
                    flag = false;
                }
                case "2" -> {
                    removeBook();
                    flag = false;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);

    }

    private void myFollowers() {
        userService.getFollowed().forEach(user -> System.out.println(user.getUsername()));
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

    private void addMessage(BookClub bookClub) {
        System.out.println("Type message(one line): ");
        scanner.nextLine();
        String text = scanner.nextLine();
        Message message = new Message(text);
        bookClubService.addMessage(bookClub, message);
    }

    private void myBookClub() {
        Librarian librarian = (Librarian) userService.getCurrentUser().get();
        System.out.println("My posts: ");
        bookClubService.printMessages(librarian.getBookClub());
        System.out.println("""
                                                                      
                                   0 -> Go back
                                   1 -> Add new message
                                                                      
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
                    addMessage(librarian.getBookClub());
                    flag = false;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);
    }

    public void start() {
        shelfService = new ShelfServiceImpl();
        System.out.println("""
                                     
                                   0 -> Log out                                 
                                   1 -> My recommended books
                                   2 -> My followers
                                   3 -> My book club
                                                                      
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
                    myBooks();
                    flag = false;
                }
                case "2" -> {
                    myFollowers();
                    flag = false;
                }
                case "3" -> {
                    myBookClub();
                    flag = false;
                }
                default -> generalMenu.invalidMessage("Invalid option.");
            }
        } while (flag);
        start();
    }
}