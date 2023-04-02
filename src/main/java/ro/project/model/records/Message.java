package ro.project.model.records;

import java.time.LocalDateTime;

public record Message(LocalDateTime sentTime, String text) {
    public Message(String text) {
        this(LocalDateTime.now(), text);
    }

    @Override
    public String toString() {
        return text;
    }
}
