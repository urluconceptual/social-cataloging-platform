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
public class Librarian extends User {
    @ToString.Exclude
    @Builder.Default
    private List<UUID> curatedBookClubIds = new ArrayList<>();
    @ToString.Exclude
    @Builder.Default
    private PersonalShelf curatedRecommendationsList = new PersonalShelf();

}
