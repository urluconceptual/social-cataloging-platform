package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.User;

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
    private BookClub bookClub = new BookClub();
    @ToString.Exclude
    private UUID recommendationsList;

}
