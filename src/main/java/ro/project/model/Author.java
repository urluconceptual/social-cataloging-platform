package ro.project.model;

import ro.project.model.abstracts.User;

import java.util.List;
import java.util.UUID;

public class Author extends User {
    private List<UUID> writtenBooks;
    private List<UUID> followers;
    private List<String> influences;
    private double averageRating;

}
