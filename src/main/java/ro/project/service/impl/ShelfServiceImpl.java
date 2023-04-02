package ro.project.service.impl;

import ro.project.model.*;
import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;
import ro.project.service.*;

import java.util.*;

public class ShelfServiceImpl implements ShelfService {
    public static Map<UUID, Shelf> shelfMap = new HashMap<>();
    public static UserService userService = new UserServiceImpl();

    public static BookService bookService = new BookServiceImpl();

    public static ReviewService reviewService = new ReviewServiceImpl();
    public static ReadingChallengeService readingChallengeService = new ReadingChallengeServiceImpl();

    @Override
    public Optional<Shelf> getById(UUID id) {
        if (shelfMap.containsKey(id)) {
            return Optional.of(shelfMap.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void addShelf(Shelf shelf) {
        shelfMap.put(shelf.getId(), shelf);
        if (shelf instanceof PersonalShelf personalShelf) {
            User owner = userService.getById(personalShelf.getOwner()).get();
            if (owner instanceof Reader reader) {
                reader.getShelves().add(shelf.getId());
            } else if (owner instanceof Author author) {
                author.setBookIdList(shelf.getId());
            } else if (owner instanceof Librarian librarian) {
                librarian.setCuratedRecommendationsList(shelf.getId());
            }
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
            int i = 1;
            for (UUID bookId : personalShelf.getBookList()) {
                System.out.println("---- " + i + ":");
                bookService.printBookData(bookId);
                System.out.println();
                i++;
            }
        } else {
            SharedShelf sharedShelf = (SharedShelf) getById(id).get();
            int i = 1;
            for (Map.Entry<UUID, UUID> entry : sharedShelf.getEditorBookMap().entrySet()) {
                System.out.println("---- " + i + ":");
                System.out.println("Collaborator:");
                userService.printUserData(entry.getKey());
                System.out.println("Added book:");
                bookService.printBookData(entry.getValue());
                System.out.println();
                i++;
            }
        }
    }

    @Override
    public List<UUID> getShelfBooks(UUID id) {
        Shelf shelf = getById(id).get();
        if (shelf instanceof PersonalShelf personalShelf) {
            return new ArrayList<>(personalShelf.getBookList());
        } else {
            return new ArrayList<>(((SharedShelf) shelf).getEditorBookMap().values());
        }
    }

    @Override
    public void addBookToShelf(UUID shelfId, UUID bookId) {
        Shelf shelf = getById(shelfId).get();
        if (shelf instanceof PersonalShelf personalShelf) {
            personalShelf.getBookList().add(bookId);
            if ("read".equals(personalShelf.getName()) && readingChallengeService.checkChallenge()) {
                readingChallengeService.addDone();
            }

        } else {
            ((SharedShelf) shelf).getEditorBookMap().put(userService.getCurrentUser().get().getId(), bookId);
        }
    }

    @Override
    public void removeBookFromShelf(UUID shelfId, UUID bookId) {
        Shelf shelf = getById(shelfId).get();
        if (shelf instanceof PersonalShelf personalShelf) {
            personalShelf.getBookList().remove(bookId);
        } else {
            ((SharedShelf) shelf).getEditorBookMap().values().remove(bookId);
        }
    }

    @Override
    public void removeBookFromAllShelves(UUID bookId) {
        for(Shelf shelf : shelfMap.values()) {
            removeBookFromShelf(shelf.getId(), bookId);
        }
    }


}
