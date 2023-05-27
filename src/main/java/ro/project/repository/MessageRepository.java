package ro.project.repository;

import ro.project.generics.Creatable;
import ro.project.model.records.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends Creatable<Message> {
    public List<Message> getById(UUID bookClubId);

    public List<Message> getAll();
}
