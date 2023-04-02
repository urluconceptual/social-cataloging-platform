package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.AbstractEntity;
import ro.project.model.enums.BookGenre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Book extends AbstractEntity {
    private String author;
    @Builder.Default
    private Optional<UUID> authorId = Optional.empty();
    private String title;
    private BookGenre genre;
    private Integer numberOfPages;
    @Builder.Default
    private Double rating = 0.0;
    @Builder.Default
    private List<UUID> reviewList = new ArrayList<>();
}
