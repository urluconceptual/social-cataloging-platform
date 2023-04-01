package ro.project.service.impl;

import ro.project.model.*;
import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;
import ro.project.service.ShelfService;
import ro.project.service.UserService;

import java.util.*;

public class ShelfServiceImpl implements ShelfService {
    public static Map<UUID, Shelf> shelfMap = new HashMap<>();
    public static UserService userService = new UserServiceImpl();

    @Override
    public Optional<Shelf> getById(UUID id) {
        if (shelfMap.containsKey(id))
            return Optional.of(shelfMap.get(id));
        else
            return Optional.empty();
    }

    @Override
    public void addShelf(Shelf shelf) {
        shelfMap.put(shelf.getId(), shelf);
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
            shelfMap.remove(id);
            shelfMap.put(id, newShelf);
        }
    }

    @Override
    public void removeShelfById(UUID id) {
        if (getById(id).isPresent()) {
            shelfMap.remove(id);
        }
    }

    @Override
    public void printShelfData(UUID id) {
        if (getById(id).get() instanceof PersonalShelf personalShelf) {
            personalShelf.getBookList().forEach(System.out::println);
        }
        else {
            SharedShelf sharedShelf = (SharedShelf) getById(id).get();
            sharedShelf.getEditorBookMap().forEach((user, book) -> System.out.println(book.toString()+" (" +user.toString()+ " )"));
        }
    }


}
