package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)

public class ReadingChallenge extends AbstractEntity {
    private Integer goal;
    @Builder.Default
    private Integer done = 0;
    @Builder.Default
    private Double donePercent = 0.0;
    @Builder.Default
    private Boolean isClosed = false;
}
