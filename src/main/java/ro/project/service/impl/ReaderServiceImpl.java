package ro.project.service.impl;

import ro.project.model.abstracts.User;
import ro.project.service.ReaderService;
import ro.project.service.UserService;

import java.util.Set;

public class ReaderServiceImpl implements ReaderService {
    private static UserService userService = new UserServiceImpl();

    @Override
    public Set<User> getFriends() {
        Set<User> friends = userService.getFollowing();
        friends.addAll(userService.getFollowed());
        return friends;
    }
}
