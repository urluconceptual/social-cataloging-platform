package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.Shelf;

import java.util.*;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PersonalShelf extends Shelf {
    private UUID owner;
    private String name;
    @Builder.Default
    private Set<UUID> bookList = new HashSet<>();
}