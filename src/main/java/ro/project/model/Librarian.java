package ro.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;

import java.util.List;
import java.util.UUID;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Librarian extends User {
    @ToString.Exclude private List<UUID> curatedBookClubIds;
    @ToString.Exclude private List<Shelf> curatedRecommendationsList;
}
