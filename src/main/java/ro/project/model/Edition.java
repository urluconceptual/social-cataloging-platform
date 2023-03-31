package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.AbstractEntity;
import ro.project.model.enums.EditionFormat;

import java.time.LocalDate;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class Edition extends AbstractEntity {
    private EditionFormat format;
    private LocalDate publishingDate;
    private String publisher;
}
