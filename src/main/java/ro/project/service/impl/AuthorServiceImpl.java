package ro.project.service.impl;

import ro.project.model.Author;
import ro.project.model.PersonalShelf;
import ro.project.service.AuthorService;

import java.util.UUID;

public class AuthorServiceImpl implements AuthorService {
    @Override
    public void printAuthorData(Author author) {

    }

    @Override
    public void addToBookList(Author author, UUID bookId) {
        author.getBookIdList().getBookList().add(bookId);
    }
}
