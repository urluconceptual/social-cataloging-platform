package ro.project.service.impl;

import ro.project.model.PersonalShelf;
import ro.project.model.Reader;
import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;
import ro.project.service.ReaderService;
import ro.project.service.ShelfService;
import ro.project.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ReaderServiceImpl implements ReaderService {
    private static UserService userService = new UserServiceImpl();
    private static ShelfService shelfService = new ShelfServiceImpl();

    @Override
    public Reader init(Reader reader) {
        shelfService.addShelves(List.of(
                PersonalShelf.builder()
                             .owner(reader.getId())
                             .name("want-to-read")
                             .bookList(new ArrayList<>())
                             .build(),
                PersonalShelf.builder()
                             .owner(reader.getId())
                             .name("currently-reading")
                             .bookList(new ArrayList<>())
                             .build(),
                PersonalShelf.builder()
                             .owner(reader.getId())
                             .name("read")
                             .bookList(new ArrayList<>())
                             .build()));
        return reader;
    }

    @Override
    public Set<User> getFriends() {
        Set<User> friends = userService.getFollowing();
        friends.retainAll(userService.getFollowed());
        return friends;
    }

    @Override
    public void removeShelf(UUID shelfId) {
        ((Reader) userService.getCurrentUser().get()).getShelves().remove(shelfId);
        shelfService.removeShelfById(shelfId);
    }

    @Override
    public void printShelves(Reader reader) {
        int i = 1;
        for(UUID shelfId : reader.getShelves()) {
            System.out.println(i + " -> " + shelfService.getById(shelfId).get().getName());
            i++;
        }
    }
}
