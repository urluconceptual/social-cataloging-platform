package ro.project.application;

import ro.project.model.*;
import ro.project.model.enums.BookGenre;
import ro.project.model.enums.UserType;
import ro.project.service.*;
import ro.project.service.impl.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PopulateScript {
    private static final Scanner scanner = new Scanner(System.in);
    private static PopulateScript INSTANCE;
    private static UserService userService = new UserServiceImpl();
    private static ReaderService readerService = new ReaderServiceImpl();

    private static AuthorService authorService = new AuthorServiceImpl();
    private static BookService bookService = new BookServiceImpl();
    private static ConnectionService connectionService = new ConnectionServiceImpl();

    private PopulateScript() {
    }

    public static PopulateScript getInstance() {
        return (INSTANCE == null ? new PopulateScript() : INSTANCE);
    }
    public static void populate() {
        addSomeUsers();
        addSomeConnections();
        addSomeBooks();
//        addSomeEditions();
//        addSomeReviews();
//        addSomeBookClubs();
//        addSomeMessages();
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
        readerService.init((Reader)userService.getByUsername("reader1").get());
        readerService.init((Reader)userService.getByUsername("reader2").get());
        readerService.init((Reader)userService.getByUsername("reader3").get());
        authorService.init((Author)userService.getByUsername("author1").get());
        authorService.init((Author)userService.getByUsername("author2").get());
        authorService.init((Author)userService.getByUsername("author3").get());
        authorService.init((Author)userService.getByUsername("author4").get());
    }

    private static void addSomeConnections() {
        connectionService.addConnections(List.of(
                Connection.builder()
                          .follower(userService.getByUsername("reader1").get().getId())
                          .followed(userService.getByUsername("reader2").get().getId())
                          .build(),
                Connection.builder()
                          .follower(userService.getByUsername("reader2").get().getId())
                          .followed(userService.getByUsername("reader1").get().getId())
                          .build(),
                Connection.builder()
                          .follower(userService.getByUsername("reader2").get().getId())
                          .followed(userService.getByUsername("reader3").get().getId())
                          .build(),
                Connection.builder()
                          .follower(userService.getByUsername("reader1").get().getId())
                          .followed(userService.getByUsername("author1").get().getId())
                          .build(),
                Connection.builder()
                          .follower(userService.getByUsername("reader2").get().getId())
                          .followed(userService.getByUsername("author1").get().getId())
                          .build(),
                Connection.builder()
                          .follower(userService.getByUsername("reader3").get().getId())
                          .followed(userService.getByUsername("author1").get().getId())
                          .build(),
                Connection.builder()
                          .follower(userService.getByUsername("reader3").get().getId())
                          .followed(userService.getByUsername("librarian1").get().getId())
                          .build(),
                Connection.builder()
                          .follower(userService.getByUsername("reader3").get().getId())
                          .followed(userService.getByUsername("librarian2").get().getId())
                          .build()));
    }

    private static void addSomeBooks() {
        bookService.addBooks(List.of(
                Book.builder()
                    .title("The Shining")
                    .authorId(Optional.of(userService.getByUsername("author1").get().getId()))
                    .author("Stephen King")
                    .genre(BookGenre.HORROR)
                    .numberOfPages(500)
                    .build(),
                Book.builder()
                    .title("It")
                    .authorId(Optional.of(userService.getByUsername("author1").get().getId()))
                    .author("Stephen King")
                    .genre(BookGenre.HORROR)
                    .numberOfPages(350)
                    .build(),
                Book.builder()
                    .title("Solenoid")
                    .authorId(Optional.of(userService.getByUsername("author2").get().getId()))
                    .author("Mircea Cartarescu")
                    .genre(BookGenre.OTHER)
                    .numberOfPages(1200)
                    .build(),
                Book.builder()
                    .title("In Search of Lost Time")
                    .authorId(Optional.of(userService.getByUsername("author3").get().getId()))
                    .author("Marcel Proust")
                    .genre(BookGenre.OTHER)
                    .numberOfPages(865)
                    .build(),
                Book.builder()
                    .title("One Hundred Years of Solitude")
                    .authorId(Optional.of(userService.getByUsername("author3").get().getId()))
                    .author("Gabriel Garcia Marquez")
                    .genre(BookGenre.HORROR)
                    .numberOfPages(450)
                    .build(),
                Book.builder()
                    .title("Nostalgia")
                    .authorId(Optional.of(userService.getByUsername("author2").get().getId()))
                    .author("Mircea Cartarescu")
                    .genre(BookGenre.HORROR)
                    .numberOfPages(1000)
                    .build(),
                Book.builder()
                    .title("The Life of Rhys Montrose")
                    .authorId(Optional.of(userService.getByUsername("author4").get().getId()))
                    .author("Rhys Montrose")
                    .genre(BookGenre.BIOGRAPHY)
                    .numberOfPages(700)
                    .build(),
                Book.builder()
                    .title("Love in the Time of the Cholera")
                    .authorId(Optional.of(userService.getByUsername("author3").get().getId()))
                    .author("Gabriel Garcia Marquez")
                    .genre(BookGenre.ROMANCE)
                    .numberOfPages(300)
                    .build() ));
        bookService.init();
    }
}
