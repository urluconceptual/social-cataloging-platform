package ro.project.repository.impl;

import ro.project.config.DatabaseConfiguration;
import ro.project.mappers.MessageMapper;
import ro.project.model.records.Message;
import ro.project.repository.MessageRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageRepositoryImpl implements MessageRepository {
    @Override
    public List<Message> getById(UUID id) {
        String selectSql = "SELECT * " +
                "FROM message " +
                "WHERE bookclubid=?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            return MessageMapper.mapToMessageList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }    @Override
    public void add(Message object) {
        String insertSql = "INSERT INTO message (date, bookclubid, text) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(object.sentTime()));
            preparedStatement.setString(2, object.BookClubId().toString());
            preparedStatement.setString(3, object.text());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getAll() {
        String selectSql = "SELECT * " +
                "FROM message ";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return MessageMapper.mapToMessageList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }    @Override
    public void addAll(List<Message> objectList) {
        objectList.forEach(this::add);
    }





}
