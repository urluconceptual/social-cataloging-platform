package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.AbstractEntity;
import ro.project.model.records.Message;

import java.time.LocalDateTime;
import java.util.*;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class BookClub extends AbstractEntity {
    @Builder.Default
    private Map<LocalDateTime, Message> messageMap = new TreeMap<>();
}
