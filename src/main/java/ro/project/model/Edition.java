package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;
import ro.project.model.enums.EditionFormat;

import java.time.LocalDate;

public class Edition extends AbstractEntity {
    private EditionFormat format;
    private LocalDate publishingDate;
    private String publisher;
}
