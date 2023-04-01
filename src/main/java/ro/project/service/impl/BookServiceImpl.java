package ro.project.service.impl;

import ro.project.model.Book;
import ro.project.service.BookService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BookServiceImpl implements BookService {
    private static Map<UUID, Book> bookMap = new HashMap<>();

}
