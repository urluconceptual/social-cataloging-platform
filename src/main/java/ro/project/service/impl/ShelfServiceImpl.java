package ro.project.service.impl;

import ro.project.model.PersonalShelf;
import ro.project.model.SharedShelf;
import ro.project.model.abstracts.Shelf;
import ro.project.repository.ShelfRepository;
import ro.project.repository.impl.ShelfRepositoryImpl;
import ro.project.service.*;

import java.util.*;

public class ShelfServiceImpl implements ShelfService {
    private static ShelfRepository shelfRepository = new ShelfRepositoryImpl();

    private static UserService userService = new UserServiceImpl();

    private static BookService bookService = new BookServiceImpl();

    private static ReviewService reviewService = new ReviewServiceImpl();
    private static ReadingChallengeService readingChallengeService = new ReadingChallengeServiceImpl();

    @Override
    public Optional<Shelf> getById(UUID id) {
        return shelfRepository.getById(id);
    }

    @Override
    public void addShelf(Shelf shelf) {
        shelfRepository.add(shelf);
    }

    @Override
    public void addShelves(List<Shelf> shelfList) {
        shelfList.forEach(this::addShelf);
    }

    @Override
    public void editShelfById(UUID id, Shelf newShelf) {
        shelfRepository.updateById(id, newShelf);
    }

    @Override
    public void removeShelfById(UUID id) {
        shelfRepository.deleteById(id);
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
        List<Shelf> shelves = shelfRepository.getAll();
        Iterator<Shelf> shelfIterator = shelves.iterator();
        while (shelfIterator.hasNext()) {
            Shelf shelf = shelfIterator.next();
            removeBookFromShelf(shelf.getId(), bookId);
        }
    }

    @Override
    public Double getShelfAverage(UUID shelfId) {
        Shelf shelf = getById(shelfId).get();
        if (shelf instanceof PersonalShelf personalShelf) {
            Double sum =
                    personalShelf.getBookList().stream().mapToDouble(b -> bookService.getById(b).get().getRating()).sum();
            Integer total = personalShelf.getBookList().size();
            return (sum / total * 100) / 100.0;
        } else {
            return -1.0;
        }
    }


}
