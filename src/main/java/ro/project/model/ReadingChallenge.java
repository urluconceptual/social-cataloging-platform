package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;

public class ReadingChallenge extends AbstractEntity {
    private Integer goal;
    private Integer done;
    private Double donePercent;
    private Boolean isClosed;
}
