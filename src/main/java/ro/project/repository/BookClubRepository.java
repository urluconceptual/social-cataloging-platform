package ro.project.repository;

import ro.project.generics.Creatable;
import ro.project.generics.Deletable;
import ro.project.generics.Readable;
import ro.project.generics.Updatable;
import ro.project.model.BookClub;

public interface BookClubRepository extends Creatable<BookClub>, Readable<BookClub>, Updatable<BookClub>,
        Deletable<BookClub> {
}
