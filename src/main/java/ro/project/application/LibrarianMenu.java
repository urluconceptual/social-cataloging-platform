package ro.project.application;

import ro.project.exceptions.OptionException;
import ro.project.model.Book;
import ro.project.model.BookClub;
import ro.project.model.Librarian;
import ro.project.model.records.Message;
import ro.project.service.*;
import ro.project.service.impl.*;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class LibrarianMenu extends TemplateMenu {
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
        if (INSTANCE == null) {
            INSTANCE = new LibrarianMenu();
        }
        return INSTANCE;
    }

    private void addNewBook() {
        Librarian librarian = (Librarian) userService.getCurrentUser().get();
        List<Book> bookList = bookService.getListOfAllBooks();
        showAllBooks();
        String input;
        int n;
        try {
            System.out.println("Enter index of book you want to recommend:");
            input = scanner.next();
            n = Integer.parseInt(input);
            if (n > bookList.size()) {
                throw new OptionException();
            }
            shelfService.addBookToShelf(librarian.getRecommendationsListId(), bookList.get(n - 1).getId());
        } catch (OptionException e) {
            System.out.println(e.getMessage());
            addNewBook();
        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer.");
            addNewBook();
        }
    }

    private void removeBook() {
        Librarian librarian = (Librarian) userService.getCurrentUser().get();
        List<UUID> bookList = librarianService.getRecommendedBooks(librarian);
        System.out.println("Enter index of book you want to remove: ");
        String input;
        int n;
        do {
            try {
                input = scanner.next();
                n = Integer.parseInt(input);
                if (n > bookList.size()) {
                    throw new OptionException();
                }
                shelfService.removeBookFromShelf(librarian.getRecommendationsListId(), bookList.get(n - 1));
                System.out.println("Successfully removed book from shelf!");
                break;
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            }
        } while (true);
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
        do {
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
                break;
            } catch (OptionException e) {
                System.out.println(e.getMessage());
                myBooks();
            }
        } while (true);

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
        Message message = new Message(bookClub.getId(), text);
        bookClubService.addMessage(bookClub, message);
    }

    private void myBookClub() {
        Librarian librarian = (Librarian) userService.getCurrentUser().get();
        System.out.println("My posts: ");
        bookClubService.printMessages(librarian.getBookClub().getId());
        System.out.println("""
                                                                      
                                   0 -> Go back
                                   1 -> Add new message
                                                                      
                                   Choose option:""");
        String options;
        do {
            try {
                options = scanner.next();
                switch (options) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> addMessage(librarian.getBookClub());
                    default -> throw new OptionException();
                }
                break;
            } catch (OptionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    @Override
    protected void welcomeMessage() {
    }

    @Override
    protected void showOptions() {
        System.out.println("""
                                     
                                   0 -> Log out                                 
                                   1 -> My recommended books
                                        -> Add new book
                                        -> Remove book
                                   2 -> My followers
                                   3 -> My book club
                                        -> Add new message
                                                                      
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
                case "3" -> myBookClub();
                default -> throw new OptionException();
            }
        } catch (OptionException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void redirect() {
        menu();
    }
}