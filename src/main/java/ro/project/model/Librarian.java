package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Librarian extends User {
    @ToString.Exclude
    @Builder.Default
    private List<UUID> curatedBookClubIds = new ArrayList<>();
    @ToString.Exclude
    private UUID curatedRecommendationsList;

}
