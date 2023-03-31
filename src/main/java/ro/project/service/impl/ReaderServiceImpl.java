package ro.project.service.impl;

import ro.project.model.PersonalShelf;
import ro.project.model.Reader;
import ro.project.model.abstracts.User;
import ro.project.service.ReaderService;
import ro.project.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReaderServiceImpl implements ReaderService {
    private static UserService userService = new UserServiceImpl();

    @Override
    public Reader init(Reader reader) {
        reader.getShelves().addAll(List.of(
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
        friends.addAll(userService.getFollowed());
        return friends;
    }
}
