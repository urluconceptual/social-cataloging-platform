package ro.project.model;

import ro.project.model.abstracts.Shelf;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SharedShelf extends Shelf {
    private List<UUID> ownerIdList;
    private String name;
    private Map<UUID,UUID> editorBookMap;
}
