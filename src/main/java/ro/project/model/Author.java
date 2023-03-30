package ro.project.model;

import ro.project.model.abstracts.User;

import java.util.List;
import java.util.UUID;

public class Author extends User {
    private List<UUID> bookIdList;
    private List<UUID> followerIdList;
    private List<String> influencesList;
    private double averageRating;

}
