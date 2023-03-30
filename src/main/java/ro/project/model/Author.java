package ro.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.User;

import java.util.List;
import java.util.UUID;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Author extends User {
    private List<UUID> bookIdList;
    private List<UUID> followerIdList;
    private List<String> influencesList;
    private double averageRating;
}
