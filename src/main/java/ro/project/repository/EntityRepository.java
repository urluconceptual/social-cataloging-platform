package ro.project.repository;

import ro.project.generics.Creatable;
import ro.project.generics.Deletable;
import ro.project.generics.Updatable;
import ro.project.model.abstracts.AbstractEntity;

public interface EntityRepository extends Creatable<AbstractEntity>, Updatable<AbstractEntity>, Deletable<AbstractEntity> {
}
