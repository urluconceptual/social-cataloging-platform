package ro.project.repository.impl;

import ro.project.config.DatabaseConfiguration;
import ro.project.repository.UserShelfRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserShelfRepositoryImpl implements UserShelfRepository {
    @Override
    public void add(String userId, String shelfId) {
        String insertSql = "INSERT INTO usershelf (userid, shelfid) VALUES (?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, shelfId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAll(List<List<String>> objectList) {
        for (List<String> strings : objectList) {
            add(strings.get(0), strings.get(1));
        }
    }

}
