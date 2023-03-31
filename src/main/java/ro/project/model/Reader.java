package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.Shelf;
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
    private List<Shelf> shelves = new ArrayList<>();

    {
        PersonalShelf wantToReadList = PersonalShelf.builder()
                                                    .owner(this.getId())
                                                    .name("want-to-read")
                                                    .bookList(new ArrayList<>())
                                                    .build();
        PersonalShelf currentlyReadingList = PersonalShelf.builder()
                                                          .owner(this.getId())
                                                          .name("currently-reading")
                                                          .bookList(new ArrayList<>())
                                                          .build();
        PersonalShelf readList = PersonalShelf.builder()
                                              .owner(this.getId())
                                              .name("read")
                                              .bookList(new ArrayList<>())
                                              .build();
        shelves = List.of(wantToReadList, currentlyReadingList, readList);
    }
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