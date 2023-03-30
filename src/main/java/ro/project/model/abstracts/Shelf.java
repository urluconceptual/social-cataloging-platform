package ro.project.model.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.enums.ShelfVisibilityType;

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