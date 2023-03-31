package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;
import ro.project.model.enums.EditionFormat;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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
