package ro.project.service;

import ro.project.model.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    void addReview(UUID bookId, Review review);

    List<Review> sorted(List<Review> reviewList);
}
