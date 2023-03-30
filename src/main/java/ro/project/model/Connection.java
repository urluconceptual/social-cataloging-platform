package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;

import java.util.UUID;

public class Connection extends AbstractEntity {
    private UUID userId1;
    private UUID userId2;
    private boolean oneFollowsTwo;
    private boolean twoFollowsOne;
}
