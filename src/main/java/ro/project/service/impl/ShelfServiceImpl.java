package ro.project.service.impl;

import ro.project.model.*;
import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;
import ro.project.service.ShelfService;
import ro.project.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShelfServiceImpl implements ShelfService {
    public static List<Shelf> shelves = new ArrayList<>();
    public static UserService userService = new UserServiceImpl();

    @Override
    public Optional<Shelf> getById(UUID id) {
        return shelves.stream()
                      .filter(shelf -> shelf.getId().equals(id))
                      .findFirst();
    }

    @Override
    public void addShelf(Shelf shelf) {
        shelves.add(shelf);
        if (shelf instanceof PersonalShelf) {
            User owner = userService.getById(((PersonalShelf) shelf).getOwner()).get();
            ((Reader) owner).getShelves().add(shelf);
        }
        if (shelf instanceof SharedShelf) {
            ((SharedShelf) shelf).getOwnerIdList()
                                 .forEach(user -> ((Reader) userService.getById(user)
                                                                       .get())
                                         .getShelves().add(shelf));
        }
    }

    @Override
    public void addShelves(List<Shelf> shelfList) {
        shelves.addAll(shelfList);
    }

    @Override
    public void editShelfById(UUID id, Shelf newShelf) {
        if (getById(id).isPresent()) {
            shelves.remove(getById(id));
            shelves.add(newShelf);
        }
    }

    @Override
    public void removeShelfById(UUID id) {
        if (getById(id).isPresent()) {
            shelves.remove(getById(id));
        }
    }


}
