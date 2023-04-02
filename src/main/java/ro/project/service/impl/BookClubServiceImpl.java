package ro.project.service.impl;

import ro.project.model.BookClub;
import ro.project.service.BookClubService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BookClubServiceImpl implements BookClubService {
    Map<UUID, BookClub> bookClubMap = new HashMap<>();
}
