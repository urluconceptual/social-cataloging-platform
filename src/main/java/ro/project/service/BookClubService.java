package ro.project.service;

import ro.project.model.BookClub;
import ro.project.model.records.Message;

import java.util.UUID;

public interface BookClubService {

    void printMessages(UUID bookClubId);

    void addMessage(BookClub bookClub, Message message);
}
