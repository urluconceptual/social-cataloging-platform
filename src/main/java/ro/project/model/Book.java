package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;
import ro.project.model.enums.BookGenre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Book extends AbstractEntity {
    private String author;
    private Optional<UUID> authorId;
    private String title;
    private BookGenre genre;
    private LocalDate publicationDate;
    private String description;
    private Integer numberOfPages;
    private Double rating;
    private List<Review> reviewList;
    private List<Edition> editionList;
}
