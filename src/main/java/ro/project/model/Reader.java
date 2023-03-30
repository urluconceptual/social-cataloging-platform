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
@EqualsAndHashCode
@ToString(callSuper = true)
public class Reader extends User {
    @ToString.Exclude private final PersonalShelf wantToReadList = PersonalShelf.builder()
                                                              .owner(this.getId())
                                                              .name("want-to-read")
                                                              .bookList(new ArrayList<>())
                                                              .build();
    @ToString.Exclude private final PersonalShelf currentlyReadingList = PersonalShelf.builder()
                                                                    .owner(this.getId())
                                                                    .name("currently-reading")
                                                                    .bookList(new ArrayList<>())
                                                                    .build();
    @ToString.Exclude private final PersonalShelf readList = PersonalShelf.builder()
                                                        .owner(this.getId())
                                                        .name("read")
                                                        .bookList(new ArrayList<>())
                                                        .build();
    @ToString.Exclude private List<Shelf> shelves;
    @ToString.Exclude private ReadingChallenge openReadingChallenge;
    @ToString.Exclude private List<ReadingChallenge> closedReadingChallengesList;
    @ToString.Exclude private List<UUID> reviewIdList;
    @ToString.Exclude private List<UUID> joinedBookClubIdList;
    @ToString.Exclude private List<UUID> connectionIdList;
    @ToString.Exclude private double averageRating;
}