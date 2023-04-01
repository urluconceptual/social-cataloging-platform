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
public class Author extends User {
    @ToString.Exclude
    private UUID bookIdList;
    @ToString.Exclude
    @Builder.Default
    private double averageRating = 0;
}
