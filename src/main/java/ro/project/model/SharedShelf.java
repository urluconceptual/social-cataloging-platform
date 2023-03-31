package ro.project.model;

import ro.project.model.abstracts.Shelf;

import java.util.*;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SharedShelf extends Shelf {
    @Builder.Default
    private List<UUID> ownerIdList = new ArrayList<>();
    private String name;
    @Builder.Default
    private Map<UUID, UUID> editorBookMap = new HashMap<>();
}
