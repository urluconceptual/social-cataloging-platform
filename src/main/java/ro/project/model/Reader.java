package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.User;

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
    private List<UUID> shelves = new ArrayList<>();
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
}