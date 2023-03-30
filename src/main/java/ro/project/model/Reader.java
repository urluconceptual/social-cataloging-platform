package ro.project.model;

import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;

import java.util.List;
import java.util.UUID;

public class Reader extends User {
    private List<UUID> wantToReadList;
    private List<UUID> currentlyReadingList;
    private List<UUID> readList;
    private List<Shelf> shelves;
    private ReadingChallenge openReadingChallange;
    private List<ReadingChallenge> closedReadingChallengesList;
    private List<UUID> reviewIdList;
    private List<UUID> joinedBookClubIdList;
    private List<UUID> connectionIdList;

    private double averageRating;
}