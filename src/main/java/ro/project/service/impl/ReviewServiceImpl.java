package ro.project.service.impl;

import ro.project.model.Review;
import ro.project.service.BookService;
import ro.project.service.ReviewService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReviewServiceImpl implements ReviewService {
    private static BookService bookService = new BookServiceImpl();

    @Override
    public void addReview(UUID bookId, Review review) {
        bookService.addReview(bookId, review);
    }

    @Override
    public List<Review> sorted(List<Review> reviewList) {
        return reviewList.stream().sorted((r1, r2) -> r2.getRating().compareTo(r1.getRating())).collect(Collectors.toList());
    }
}
