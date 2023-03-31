package ro.project.service;

import ro.project.model.Reader;
import ro.project.model.abstracts.User;

import java.util.Set;

public interface ReaderService {
    Reader init(Reader reader);
    Set<User> getFriends();
}