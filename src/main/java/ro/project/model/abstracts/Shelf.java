package ro.project.model.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.enums.ShelfType;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class Shelf extends AbstractEntity {
    private String name;
    private ShelfType type;
}