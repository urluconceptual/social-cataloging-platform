package ro.project.model;

import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Reader extends User {
    private final PersonalShelf wantToReadList = PersonalShelf.builder()
                                                              .owner(this.getId())
                                                              .name("want-to-read")
                                                              .bookList(new ArrayList<>())
                                                              .build();
    private final PersonalShelf currentlyReadingList = PersonalShelf.builder()
                                                                    .owner(this.getId())
                                                                    .name("currently-reading")
                                                                    .bookList(new ArrayList<>())
                                                                    .build();
    private final PersonalShelf readList = PersonalShelf.builder()
                                                       .owner(this.getId())
                                                       .name("read")
                                                       .bookList(new ArrayList<>())
                                                       .build();
    private List<Shelf> shelves;
    private ReadingChallenge openReadingChallenge;
    private List<ReadingChallenge> closedReadingChallengesList;
    private List<UUID> reviewIdList;
    private List<UUID> joinedBookClubIdList;
    private List<UUID> connectionIdList;
    private double averageRating;
}