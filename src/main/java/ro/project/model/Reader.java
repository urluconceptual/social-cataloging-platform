package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Reader extends User {
    @ToString.Exclude
    @Builder.Default
    private List<UUID> shelves = new ArrayList<>();
    @ToString.Exclude
    @Builder.Default
    private Optional<ReadingChallenge> readingChallenge = Optional.empty();
    @ToString.Exclude
    @Builder.Default
    private List<Review> reviewList = new ArrayList<>();
    @ToString.Exclude
    @Builder.Default
    private double averageRating = 0;
}