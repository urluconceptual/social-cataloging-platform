package ro.project.model;

import ro.project.model.enums.EditionFormat;

import java.time.LocalDate;

public record Edition(EditionFormat format, LocalDate publishingDate, String publisher) { }