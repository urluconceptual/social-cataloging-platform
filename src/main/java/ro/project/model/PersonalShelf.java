package ro.project.model;

import ro.project.model.abstracts.Shelf;

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
public class PersonalShelf extends Shelf {
    private UUID owner;
    private String name;
    private List<UUID> bookList;
}