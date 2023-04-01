package ro.project.service.impl;

import ro.project.model.Author;
import ro.project.model.Book;
import ro.project.service.AuthorService;
import ro.project.service.BookService;
import ro.project.service.UserService;

import java.util.*;

public class BookServiceImpl implements BookService {
    private static Map<UUID, Book> bookMap = new HashMap<>();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static UserService userService = new UserServiceImpl();

    @Override
    public void init() {
        bookMap.forEach((id, book) -> {
            if (book.getAuthorId().isPresent())
                authorService.addToBookList((Author) userService.getById(book.getAuthorId().get()).get(), book.getId());
        });
    }

    @Override
    public Optional<Book> getById(UUID id) {
        if (bookMap.containsKey(id))
            return Optional.of(bookMap.get(id));
        else
            return Optional.empty();
    }

    @Override
    public void addBook(Book book) {
        bookMap.put(book.getId(), book);
    }

    @Override
    public void addBooks(List<Book> bookList) {
        bookList.forEach(this::addBook);
    }

    @Override
    public void editBookById(UUID id, Book newBook) {
        bookMap.remove(id);
        bookMap.put(id, newBook);
    }

    @Override
    public void removeBookById(UUID id) {
        bookMap.remove(id);
    }

    @Override
    public void printBookData(UUID id) {
        Book book = getById(id).get();
        System.out.println(
                "title: " + book.getTitle() +
                        "\nauthor: " + book.getAuthor() +
                        "\ngenre: " + book.getGenre().getName() +
                        "\nnumber of pages: " + book.getNumberOfPages() +
                        "\nrating: " + book.getRating() +
                        "\n"
                          );
    }

    @Override
    public List<Book> getListOfAllBooks() {
        return new ArrayList<>(bookMap.values());
    }

}
