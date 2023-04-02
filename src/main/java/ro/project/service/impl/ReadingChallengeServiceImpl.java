package ro.project.service.impl;

import ro.project.model.Reader;
import ro.project.model.ReadingChallenge;
import ro.project.service.ReadingChallengeService;
import ro.project.service.UserService;

import java.util.Optional;

public class ReadingChallengeServiceImpl implements ReadingChallengeService {
    private static UserService userService = new UserServiceImpl();

    @Override
    public void printStatus() {
        Reader currentReader = (Reader) userService.getCurrentUser().get();
        if (currentReader.getReadingChallenge().isEmpty()) {
            System.out.println("You did not set any reading goals yet!");
        } else {
            ReadingChallenge readingChallenge = currentReader.getReadingChallenge().get();
            System.out.println("YOUR READING CHALLENGE:");
            System.out.println();
            System.out.println("goal: " + readingChallenge.getGoal());
            System.out.println("done: " + readingChallenge.getDone());
            System.out.println("progress: " + readingChallenge.getDonePercent() + "% -> " +
                                       (readingChallenge.getDonePercent() >= 100.0 ? " Congrats!" : " Almost there!"));
        }
    }

    @Override
    public void setNewChallenge(int n) {
        Reader currentReader = (Reader) userService.getCurrentUser().get();
        currentReader.setReadingChallenge(Optional.of(ReadingChallenge.builder()
                                                                      .goal(n)
                                                                      .build()));
    }

    @Override
    public boolean checkChallenge() {
        Reader currentReader = (Reader) userService.getCurrentUser().get();
        return currentReader.getReadingChallenge().isPresent();
    }

    @Override
    public void addDone() {
        Reader currentReader = (Reader) userService.getCurrentUser().get();
        int goal = currentReader.getReadingChallenge().get().getGoal();
        int done = currentReader.getReadingChallenge().get().getDone() + 1;
        currentReader.getReadingChallenge().get().setDone(done);
        currentReader.getReadingChallenge().get().setDonePercent(((int) ((double) done / goal * 10000)) / 100.0);
    }
}
