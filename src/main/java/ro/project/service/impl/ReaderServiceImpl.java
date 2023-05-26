package ro.project.service.impl;

import ro.project.model.PersonalShelf;
import ro.project.model.Reader;
import ro.project.model.Review;
import ro.project.model.abstracts.User;
import ro.project.model.enums.ShelfType;
import ro.project.service.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ReaderServiceImpl implements ReaderService {
    private static UserService userService = new UserServiceImpl();
    private static ShelfService shelfService = new ShelfServiceImpl();
    private static BookService bookService = new BookServiceImpl();
    private static ReviewService reviewService = new ReviewServiceImpl();

    @Override
    public Reader init(Reader reader) {
        shelfService.addShelves(List.of(
                PersonalShelf.builder()
                             .type(ShelfType.PERSONAL)
                             .owner(reader.getId())
                             .name("want-to-read")
                             .bookList(new HashSet<>())
                             .build(),
                PersonalShelf.builder()
                             .type(ShelfType.PERSONAL)
                             .owner(reader.getId())
                             .name("currently-reading")
                             .bookList(new HashSet<>())
                             .build(),
                PersonalShelf.builder()
                             .type(ShelfType.PERSONAL)
                             .owner(reader.getId())
                             .name("read")
                             .bookList(new HashSet<>())
                             .build()));
        return reader;
    }

    @Override
    public Set<User> getFriends() {
        Set<User> friends = userService.getFollowing();
        friends.retainAll(userService.getFollowed());
        return friends;
    }

    @Override
    public void removeShelf(UUID shelfId) {
        ((Reader) userService.getCurrentUser().get()).getShelves().remove(shelfId);
        shelfService.removeShelfById(shelfId);
    }

    @Override
    public void printShelves(Reader reader) {
        int i = 1;
        for (UUID shelfId : reader.getShelves()) {
            System.out.println(i + " -> " + shelfService.getById(shelfId).get().getName());
            i++;
        }
    }

    @Override
    public void addReview(Review review) {
        ((Reader) userService.getCurrentUser().get()).getReviewList().add(review);
    }

    @Override
    public void printTopReviews() {
        Reader reader = (Reader) userService.getCurrentUser().get();
        List<Review> reviewList = reviewService.sorted(reader.getReviewList());
        reviewList.forEach(r -> {
            System.out.println("---- YOUR RATING:");
            System.out.println(r.getRating() + "/10");
            System.out.println("---- BOOK:");
            bookService.printBookData(r.getBookId());

        });
    }


}
