package ro.project.service;

import ro.project.model.abstracts.User;

import java.util.Set;

public interface ReaderService {
    Set<User> getFriends();
}