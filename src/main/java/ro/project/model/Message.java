package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractEntity {
    @Builder.Default
    private LocalDateTime sentTime = LocalDateTime.now();
    private UUID userId;
    private String text;

}
