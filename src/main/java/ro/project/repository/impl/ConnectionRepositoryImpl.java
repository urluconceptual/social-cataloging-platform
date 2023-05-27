package ro.project.repository.impl;

import ro.project.config.DatabaseConfiguration;
import ro.project.mappers.ConnectionMapper;
import ro.project.model.Connection;
import ro.project.repository.ConnectionRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ConnectionRepositoryImpl implements ConnectionRepository {
    private static EntityRepositoryImpl entityRepository = new EntityRepositoryImpl();

    @Override
    public Optional<Connection> getById(UUID id) {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN connection ON Entity.id = connection.id " +
                "WHERE Entity.id=?";
        try (java.sql.Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            return ConnectionMapper.mapToConnection(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }    @Override
    public void add(Connection object) {
        entityRepository.add(object);
        String insertSql = "INSERT INTO connection (id, followed, follower) VALUES (?, ?, ?)";

        try (java.sql.Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, object.getId().toString());
            preparedStatement.setString(2, object.getFollowed().toString());
            preparedStatement.setString(3, object.getFollower().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Connection> getAll() {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN connection ON Entity.id = connection.id " +
                "WHERE Entity.id=?";
        try (java.sql.Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return ConnectionMapper.mapToConnectionList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }    @Override
    public void addAll(List<Connection> objectList) {
        objectList.forEach(this::add);
    }

    @Override
    public void updateById(UUID id, Connection newObject) {
        deleteById(id);
        add(newObject);
    }

    @Override
    public void deleteById(UUID id) {
        entityRepository.deleteById(id);
    }

    @Override
    public List<Connection> getAllByUserId(UUID userId) {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN connection ON Entity.id = connection.id " +
                "WHERE followed=? OR follower=?";
        try (java.sql.Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, userId.toString());
            preparedStatement.setString(2, userId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            return ConnectionMapper.mapToConnectionList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }




}
