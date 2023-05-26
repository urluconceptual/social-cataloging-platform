package ro.project.repository.impl;

import ro.project.config.DatabaseConfiguration;
import ro.project.mappers.BookClubMapper;
import ro.project.mappers.UserMapper;
import ro.project.model.BookClub;
import ro.project.repository.BookClubRepository;
import ro.project.repository.EntityRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookClubRepositoryImpl implements BookClubRepository {
    private static EntityRepository entityRepository = new EntityRepositoryImpl();

    @Override
    public void add(BookClub object) {
        entityRepository.add(object);
        String insertSql = "INSERT INTO bookclub (id) VALUES (?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, object.getId().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAll(List<BookClub> objectList) {
        objectList.forEach(this::add);
    }

    @Override
    public void deleteById(UUID id) {
        entityRepository.deleteById(id);
    }

    @Override
    public Optional<BookClub> getById(UUID id) {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN BookClub ON Entity.id = BookClub.id " +
                "WHERE id=?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            return BookClubMapper.mapToBookClub(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<BookClub> getAll() {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN BookClub ON Entity.id = BookClub.id ";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return BookClubMapper.mapToBookClubList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void updateById(UUID id, BookClub newObject) {
        deleteById(id);
        add(newObject);
    }
}
