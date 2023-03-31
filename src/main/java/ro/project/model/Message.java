package ro.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.AbstractEntity;

import java.time.LocalDateTime;
import java.util.UUID;

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
