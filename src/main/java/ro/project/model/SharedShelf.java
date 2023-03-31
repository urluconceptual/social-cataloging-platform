package ro.project.model;

import ro.project.model.abstracts.Shelf;

import java.util.*;

public class SharedShelf extends Shelf {
    private List<UUID> ownerIdList = new ArrayList<>();
    private String name;
    private Map<UUID, UUID> editorBookMap = new HashMap<>();
}
