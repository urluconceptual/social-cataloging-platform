package ro.project.model.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public abstract class AbstractEntity {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime updateDate;
    private LocalDateTime deleteDate;
}