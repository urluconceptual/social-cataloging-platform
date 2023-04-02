package ro.project.model.records;

import java.time.LocalDateTime;
import java.util.UUID;

public record Message(LocalDateTime sentTime, UUID userId, String text) {
    public Message(UUID userId, String text) {
        this(LocalDateTime.now(), userId, text);
    }
}
