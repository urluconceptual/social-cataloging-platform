package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Reader extends User {
    @ToString.Exclude
    @Builder.Default
    private List<Shelf> shelves = new ArrayList<>();
    @ToString.Exclude
    @Builder.Default
    private ReadingChallenge openReadingChallenge = new ReadingChallenge();
    @ToString.Exclude
    @Builder.Default
    private List<ReadingChallenge> closedReadingChallengesList = new ArrayList<>();
    @ToString.Exclude
    @Builder.Default
    private List<UUID> reviewIdList = new ArrayList<>();
    @ToString.Exclude
    @Builder.Default
    private List<UUID> joinedBookClubIdList = new ArrayList<>();
    @ToString.Exclude
    @Builder.Default
    private double averageRating = 0;

    public Reader(String username, String password, String firstName, String lastName, LocalDate birthDate,
                  String bio, UserType type, List<UUID> connectionIdList, List<Shelf> shelves,
                  ReadingChallenge openReadingChallenge, List<ReadingChallenge> closedReadingChallengesList,
                  List<UUID> reviewIdList, List<UUID> joinedBookClubIdList, double averageRating) {
        super(username, password, firstName, lastName, birthDate, bio, type, connectionIdList);
        this.shelves = shelves;
        this.openReadingChallenge = openReadingChallenge;
        this.closedReadingChallengesList = closedReadingChallengesList;
        this.reviewIdList = reviewIdList;
        this.joinedBookClubIdList = joinedBookClubIdList;
        this.averageRating = averageRating;
    }
}