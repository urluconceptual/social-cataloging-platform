package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;

import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Review extends AbstractEntity {
    private UUID bookId;
    private UUID readerId;
    @Builder.Default
    private String reviewMessage = "";
    private Integer rating;
}