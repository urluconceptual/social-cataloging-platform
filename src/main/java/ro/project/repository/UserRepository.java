package ro.project.repository;

import ro.project.generics.Creatable;
import ro.project.generics.Deletable;
import ro.project.generics.Readable;
import ro.project.generics.Updatable;
import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends Creatable<User>, Readable<User>, Updatable<User>, Deletable<User> {

    Optional<User> getByUsername(String username);

    Set<User> getByType(UserType type);
}
