package ro.project.repository.impl;

import ro.project.config.DatabaseConfiguration;
import ro.project.mappers.UserMapper;
import ro.project.model.Author;
import ro.project.model.Librarian;
import ro.project.model.Reader;
import ro.project.model.abstracts.AbstractEntity;
import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;
import ro.project.repository.BookClubRepository;
import ro.project.repository.EntityRepository;
import ro.project.repository.ShelfRepository;
import ro.project.repository.UserRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserRepositoryImpl implements UserRepository {
    private static EntityRepository entityRepository = new EntityRepositoryImpl();

    private static BookClubRepository bookClubRepository = new BookClubRepositoryImpl();

    private static ShelfRepository shelfRepository = new ShelfRepositoryImpl();

    private void addShelves(User user) {
        UUID id = user.getId();
        if(user instanceof Reader) {
            ((Reader) user).getShelves().addAll(shelfRepository.getAllByUserId(id).stream().map(AbstractEntity::getId).toList());
        }
        else if (user instanceof Author) {
            List<UUID> shelves = shelfRepository.getAllByUserId(id).stream().map(AbstractEntity::getId).toList();
            ((Author) user).setBookIdList(shelves.isEmpty() ? null : shelves.get(0));
        }
        else if (user instanceof Librarian) {
            List<UUID> shelves = shelfRepository.getAllByUserId(id).stream().map(AbstractEntity::getId).toList();
            ((Librarian) user).setRecommendationsListId(shelves.isEmpty() ? null : shelves.get(0));
        }
    }

    @Override
    public Optional<User> getById(UUID id) {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN AppUser ON Entity.id = AppUser.id " +
                "LEFT JOIN Reader ON AppUser.id = Reader.id " +
                "LEFT JOIN Author ON AppUser.id = Author.id " +
                "LEFT JOIN Librarian ON AppUser.id = Librarian.id " +
                "WHERE Entity.id=?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            User user = UserMapper.mapToUser(resultSet).get();

            addShelves(user);

            return Optional.of(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN AppUser ON Entity.id = AppUser.id " +
                "LEFT JOIN Reader ON AppUser.id = Reader.id " +
                "LEFT JOIN Author ON AppUser.id = Author.id " +
                "LEFT JOIN Librarian ON AppUser.id = Librarian.id " +
                "WHERE username=?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = UserMapper.mapToUser(resultSet).get();

            addShelves(user);

            return Optional.of(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Set<User> getByType(UserType type) {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN AppUser ON Entity.id = AppUser.id " +
                "LEFT JOIN Reader ON AppUser.id = Reader.id " +
                "LEFT JOIN Author ON AppUser.id = Author.id " +
                "LEFT JOIN Librarian ON AppUser.id = Librarian.id " +
                "WHERE usertype=?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, type.getType());

            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = UserMapper.mapToUserList(resultSet);
            users.forEach(this::addShelves);
            return new HashSet<>(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new HashSet<>();
    }

    @Override
    public List<User> getAll() {
        String selectSql = "SELECT * " +
                "FROM Entity " +
                "INNER JOIN AppUser ON Entity.id = AppUser.id " +
                "LEFT JOIN Reader ON AppUser.id = Reader.id " +
                "LEFT JOIN Author ON AppUser.id = Author.id " +
                "LEFT JOIN Librarian ON AppUser.id = Librarian.id ";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = UserMapper.mapToUserList(resultSet);
            users.forEach(this::addShelves);
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void add(User user) {
        entityRepository.add(user);
        String insertSqlUser = "INSERT INTO AppUser (id, username, password, firstname, lastname, birthdate, bio, usertype) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSqlUser)) {
            preparedStatement.setString(1, user.getId().toString());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setDate(6, Date.valueOf(user.getBirthDate()));
            preparedStatement.setString(7, user.getBio());
            preparedStatement.setString(8, user.getType().getType());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(user.getType().equals(UserType.READER)) {
            String insertSqlReader = "INSERT INTO Reader (id, averagerating, readingchallengeid) VALUES (?, ?, ?)";

            try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertSqlReader)) {
                preparedStatement.setString(1, user.getId().toString());
                preparedStatement.setFloat(2, (float) ((Reader) user).getAverageRating());
                String readingChallengeId = ((Reader) user).getReadingChallenge().isEmpty() ? null : ((Reader) user).getReadingChallenge().get().getId().toString();
                preparedStatement.setString(3, readingChallengeId);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if(user.getType().equals(UserType.AUTHOR)) {
            String insertSqlAuthor = "INSERT INTO Author (id, averagerating) VALUES (?, ?)";

            try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertSqlAuthor)) {
                preparedStatement.setString(1, user.getId().toString());
                preparedStatement.setDouble(2, ((Author) user).getAverageRating());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if(user.getType().equals(UserType.LIBRARIAN)) {
            bookClubRepository.add(((Librarian) user).getBookClub());
            String insertSqlLibrarian = "INSERT INTO Librarian (id, bookclubid) VALUES (?, ?)";

            try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertSqlLibrarian)) {
                preparedStatement.setString(1, user.getId().toString());
                preparedStatement.setString(2, ((Librarian) user).getBookClub().getId().toString());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addAll(List<User> userList) {
        userList.forEach(this::add);
    }

    @Override
    public void updateById(UUID id, User newUser) {
        deleteById(id);
        add(newUser);
    }

    @Override
    public void deleteById(UUID id) {
        entityRepository.deleteById(id);
    }

}
