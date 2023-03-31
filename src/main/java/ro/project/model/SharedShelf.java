package ro.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.project.model.abstracts.Shelf;

import java.util.*;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SharedShelf extends Shelf {
    @Builder.Default
    private List<UUID> ownerIdList = new ArrayList<>();
    @Builder.Default
    private Map<UUID, UUID> editorBookMap = new HashMap<>();
}
