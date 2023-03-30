package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BookClub extends AbstractEntity {
    private UUID librarianId;
    private List<UUID> userIdList;
    private Map<LocalDateTime, Message> messageMap;
}
