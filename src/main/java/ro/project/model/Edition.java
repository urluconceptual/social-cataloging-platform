package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.enums.EditionFormat;

import java.time.LocalDate;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public record Edition(EditionFormat format, LocalDate publishingDate, String publisher) { }