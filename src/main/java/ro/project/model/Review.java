package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;

import java.util.UUID;

public class Review extends AbstractEntity {
    private UUID bookId;
    private UUID readerId;
    private String reviewMessage;
    private Integer rating;
}