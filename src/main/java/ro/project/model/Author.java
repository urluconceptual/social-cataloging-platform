package ro.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
public class Author extends User {
    @ToString.Exclude private List<UUID> bookIdList = new ArrayList<>();
    @ToString.Exclude private List<String> influencesList = new ArrayList<>();
    @ToString.Exclude private double averageRating;
}
