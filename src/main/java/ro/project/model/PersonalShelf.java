package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.Shelf;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PersonalShelf extends Shelf {
    private UUID owner;
    @Builder.Default
    private Set<UUID> bookList = new HashSet<>();
}