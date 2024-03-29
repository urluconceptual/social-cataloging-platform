package ro.project.service.impl;

import ro.project.model.Author;
import ro.project.model.Book;
import ro.project.model.Review;
import ro.project.service.*;

import java.util.*;

public class BookServiceImpl implements BookService {
    private static Map<UUID, Book> bookMap = new HashMap<>();
    private static AuthorService authorService = new AuthorServiceImpl();
    private static UserService userService = new UserServiceImpl();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static ShelfService shelfService = new ShelfServiceImpl();

    @Override
    public void init() {
        bookMap.forEach((id, book) -> {
            if (book.getAuthorId().isPresent()) {
                authorService.addToBookList((Author) userService.getById(book.getAuthorId().get()).get(), book.getId());
            }
        });
    }

    @Override
    public Optional<Book> getById(UUID id) {
        if (bookMap.containsKey(id)) {
            return Optional.of(bookMap.get(id));
        } else {
            return Optional.empty();
        }
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
        shelfService.removeBookFromAllShelves(id);
    }

    @Override
    public void printBookData(UUID id) {
        Book book = getById(id).get();
        List<Review> reviewList = book.getReviewList();
        System.out.println(
                "title: " + book.getTitle() +
                        "\nauthor: " + book.getAuthor() +
                        "\ngenre: " + book.getGenre().getName() +
                        "\nnumber of pages: " + book.getNumberOfPages() +
                        "\nrating: " + book.getRating() + "/10" +
                        "\nreviews: \n"
                          );
        reviewList.forEach(review -> System.out.println("    reader: " +
                                                                userService.getById(review.getReaderId()).get().getUsername() +
                                                                "    rating: " + review.getRating() +
                                                                "    review: " + review.getText() +
                                                                "\n"));
    }

    @Override
    public List<Book> getListOfAllBooks() {
        return new ArrayList<>(bookMap.values());
    }

    @Override
    public void addReview(UUID bookId, Review review) {
        Book book = getById(bookId).get();
        book.getReviewList().add(review);
        Integer sum = getById(bookId).get().getReviewList().stream().mapToInt(r -> r.getRating()).sum();
        Integer total = getById(bookId).get().getReviewList().size() * 10;
        book.setRating((int) ((double) sum / total * 1000) / 100.0);
        readerService.addReview(review);
        if (book.getAuthorId().isPresent()) {
            authorService.updateRating(book.getAuthorId().get());
        }
    }

}
