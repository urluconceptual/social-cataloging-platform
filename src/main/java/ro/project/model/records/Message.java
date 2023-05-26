package ro.project.model.records;

import java.time.LocalDateTime;
import java.util.UUID;

public record Message(UUID BookClubId, LocalDateTime sentTime, String text) {
    public Message(UUID BookClubId, String text) {
        this(BookClubId, LocalDateTime.now(), text);
    }

    @Override
    public String toString() {
        return text;
    }
}
