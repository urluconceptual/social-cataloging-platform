package ro.project.model;

import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;

import java.util.List;
import java.util.UUID;

public class Librarian extends User {
        private List<UUID> curatedBookClubs;
        private List<Shelf> curatedRecommendationLists;
}
