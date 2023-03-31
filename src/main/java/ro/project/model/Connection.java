package ro.project.model;

import ro.project.model.abstracts.AbstractEntity;

import java.util.UUID;
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
public class Connection extends AbstractEntity {
    private UUID userId1;
    private UUID userId2;
    private boolean oneFollowsTwo;
    private boolean twoFollowsOne;
}
