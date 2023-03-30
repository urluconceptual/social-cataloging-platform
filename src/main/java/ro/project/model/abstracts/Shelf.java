package ro.project.model.abstracts;

import ro.project.model.enums.ShelfVisibilityType;

import lombok.*;
import ro.project.model.enums.UserType;

import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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