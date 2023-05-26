package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.AbstractEntity;

import java.util.UUID;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Review extends AbstractEntity {
    private UUID bookId;
    private UUID readerId;
    @Builder.Default
    private String text = "";
    private Integer rating;
}