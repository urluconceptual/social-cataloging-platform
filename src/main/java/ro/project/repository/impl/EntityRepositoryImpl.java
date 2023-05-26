package ro.project.repository.impl;

import ro.project.config.DatabaseConfiguration;
import ro.project.model.abstracts.AbstractEntity;
import ro.project.repository.EntityRepository;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class EntityRepositoryImpl implements EntityRepository {

    @Override
    public void add(AbstractEntity object) {
        String insertSql = "INSERT INTO entity (id, creationdate, updatedate, deletedate) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, object.getId().toString());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(object.getCreationDate()));
            preparedStatement.setTimestamp(3, object.getUpdateDate() == null? null : Timestamp.valueOf(object.getUpdateDate()));
            preparedStatement.setTimestamp(4, object.getDeleteDate() == null? null : Timestamp.valueOf(object.getDeleteDate()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAll(List<AbstractEntity> objectList) {
        objectList.forEach(this::add);
    }

    @Override
    public void deleteById(UUID id) {
        String updateNameSql = "DELETE FROM entity WHERE id=?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateById(UUID id, AbstractEntity newObject) {
        String updateNameSql = "UPDATE entity SET updatedate=? WHERE id=?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(newObject.getUpdateDate()));
            preparedStatement.setString(2, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
