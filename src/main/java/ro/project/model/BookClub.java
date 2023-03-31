package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;

import java.time.LocalDateTime;
import java.util.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
