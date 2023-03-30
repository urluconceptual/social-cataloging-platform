package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Message extends AbstractEntity {
    private LocalDateTime sentTime;
    private UUID userId;
    private String text;

}
