package ro.project.model.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
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
    private LocalDate creationDate = LocalDate.now();
    private LocalDate updateDate;
    private LocalDate deleteDate;
}