package ro.project.repository;

import java.util.List;

public interface UserShelfRepository {
    public void add(String userId, String shelfId);

    public void addAll(List<List<String>> objectList);
}
