package ro.project.service.impl;

import ro.project.model.Author;
import ro.project.model.Librarian;
import ro.project.model.Reader;
import ro.project.model.SharedShelf;
import ro.project.model.abstracts.Shelf;
import ro.project.service.ShelfService;
import ro.project.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShelfServiceImpl implements ShelfService {
    public static List<Shelf> shelves = new ArrayList<>();
    public static UserService userService = new UserServiceImpl();

    static {
        if (userService.getCurrentUser().isPresent()) {
            if (userService.getCurrentUser().get() instanceof Author) {
                shelves = List.of(((Author) userService.getCurrentUser().get()).getBookIdList());
            } else if (userService.getCurrentUser().get() instanceof Librarian) {
                shelves = List.of(((Librarian) userService.getCurrentUser().get()).getCuratedRecommendationsList());
            } else if (userService.getCurrentUser().get() instanceof Reader) {
                shelves = ((Reader) userService.getCurrentUser().get()).getShelves();
            }
        }
    }

    @Override
    public Optional<Shelf> getById(UUID id) {
        return shelves.stream()
                      .filter(shelf -> shelf.getId().equals(id))
                      .findFirst();
    }

    @Override
    public void addShelf(Shelf shelf) {
        shelves.add(shelf);
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
