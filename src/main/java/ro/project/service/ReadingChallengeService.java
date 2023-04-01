package ro.project.service;

public interface ReadingChallengeService {
    void printStatus();

    void setNewChallenge(int n);

    boolean checkChallenge();

    void addDone();
}
