package ro.project.model.abstracts;

import ro.project.model.enums.ShelfVisibilityType;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class Shelf extends AbstractEntity {
    private String name;
    private ShelfVisibilityType visibilityType;
}