package ro.project.repository.impl;

import ro.project.config.DatabaseConfiguration;
import ro.project.mappers.ShelfMapper;
import ro.project.model.PersonalShelf;
import ro.project.model.SharedShelf;
import ro.project.model.abstracts.Shelf;
import ro.project.model.enums.ShelfType;
import ro.project.repository.EntityRepository;
import ro.project.repository.ShelfRepository;
import ro.project.repository.UserShelfRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShelfRepositoryImpl implements ShelfRepository {
    private static EntityRepository entityRepository = new EntityRepositoryImpl();

    private static UserShelfRepository userShelfRepository = new UserShelfRepositoryImpl();
    @Override
    public void add(Shelf object) {
        entityRepository.add(object);
        String insertSql = "INSERT INTO shelf (id, name, type) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, object.getId().toString());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setString(3, object.getType().getType());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(object.getType().equals(ShelfType.PERSONAL)) {
            userShelfRepository.add(((PersonalShelf)object).getOwner().toString(), object.getId().toString());
        }
        else {
            ((SharedShelf)object).getOwnerIdList().forEach(ownerId -> userShelfRepository.add(ownerId.toString(), object.getId().toString()));
        }
    }

    @Override
    public void addAll(List<Shelf> objectList) {
        objectList.forEach(this::add);
    }

    @Override
    public void deleteById(UUID id) {
        entityRepository.deleteById(id);
    }

    @Override
    public Optional<Shelf> getById(UUID id) {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN Shelf ON Entity.id = Shelf.id " +
                "WHERE Entity.id=?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            return ShelfMapper.mapToShelf(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Shelf> getAll() {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN Shelf ON Entity.id = Shelf.id ";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return ShelfMapper.mapToShelfList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void updateById(UUID id, Shelf newObject) {
        deleteById(id);
        add(newObject);
    }

    @Override
    public List<Shelf> getAllByUserId(UUID userId) {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN Shelf ON Entity.id = Shelf.id " +
                "INNER JOIN UserShelf ON Shelf.id = UserShelf.shelfid " +
                "WHERE UserShelf.userid=?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, userId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            return ShelfMapper.mapToShelfList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
