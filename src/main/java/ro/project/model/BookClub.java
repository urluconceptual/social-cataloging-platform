package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;

import java.time.LocalDateTime;
import java.util.*;

public class BookClub extends AbstractEntity {
    private UUID librarianId;
    private List<UUID> userIdList = new ArrayList<>();
    private Map<LocalDateTime, Message> messageMap = new TreeMap<>();
}
