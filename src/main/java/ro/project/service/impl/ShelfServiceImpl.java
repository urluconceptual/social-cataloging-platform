package ro.project.service.impl;

import ro.project.model.*;
import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;
import ro.project.service.ShelfService;
import ro.project.service.UserService;

import java.util.*;

public class ShelfServiceImpl implements ShelfService {
    public static Map<UUID, Shelf> shelves = new HashMap<>();
    public static UserService userService = new UserServiceImpl();

    @Override
    public Optional<Shelf> getById(UUID id) {
        if (shelves.containsKey(id))
            return Optional.of(shelves.get(id));
        else
            return Optional.empty();
    }

    @Override
    public void addShelf(Shelf shelf) {
        shelves.put(shelf.getId(), shelf);
        if (shelf instanceof PersonalShelf personalShelf) {
            User owner = userService.getById(personalShelf.getOwner()).get();
            ((Reader) owner).getShelves().add(shelf.getId());
        }
        if (shelf instanceof SharedShelf sharedShelf) {
            sharedShelf.getOwnerIdList()
                                 .forEach(user -> ((Reader) userService.getById(user)
                                                                       .get())
                                         .getShelves().add(shelf.getId()));
        }
    }

    @Override
    public void addShelves(List<Shelf> shelfList) {
        shelfList.forEach(this::addShelf);
    }

    @Override
    public void editShelfById(UUID id, Shelf newShelf) {
        if (getById(id).isPresent()) {
            shelves.put(id, newShelf);
        }
    }

    @Override
    public void removeShelfById(UUID id) {
        if (getById(id).isPresent()) {
            shelves.remove(id);
        }
    }


}
