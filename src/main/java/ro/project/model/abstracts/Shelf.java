package ro.project.model.abstracts;

import ro.project.model.enums.ShelfVisibilityType;

import lombok.experimental.SuperBuilder;
import lombok.Getter;

@SuperBuilder
@Getter
public abstract class Shelf extends AbstractEntity {
    private String name;
    private ShelfVisibilityType visibilityType;
}