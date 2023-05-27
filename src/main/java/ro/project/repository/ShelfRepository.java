package ro.project.repository;

import ro.project.generics.Creatable;
import ro.project.generics.Deletable;
import ro.project.generics.Readable;
import ro.project.generics.Updatable;
import ro.project.model.abstracts.Shelf;

import java.util.List;
import java.util.UUID;

public interface ShelfRepository extends Creatable<Shelf>, Readable<Shelf>, Updatable<Shelf>, Deletable<Shelf> {
    public List<Shelf> getAllByUserId(UUID userId);
}
