package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.AbstractEntity;

import java.time.LocalDateTime;
import java.util.*;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class BookClub extends AbstractEntity {
    private UUID librarianId;
    @Builder.Default
    private List<UUID> userIdList = new ArrayList<>();
    @Builder.Default
    private Map<LocalDateTime, Message> messageMap = new TreeMap<>();
}
