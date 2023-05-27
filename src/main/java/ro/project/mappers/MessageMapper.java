package ro.project.mappers;

import ro.project.model.records.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MessageMapper {
    private static MessageMapper INSTANCE;

    private MessageMapper() {
    }

    public static MessageMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessageMapper();
        }
        return INSTANCE;
    }

    public static List<Message> mapToMessageList(ResultSet resultSet) throws SQLException {
        List<Message> messages = new ArrayList<>();
        Optional<Message> message = mapToMessage(resultSet);
        while (message.isPresent()) {
            messages.add(message.get());
            message = mapToMessage(resultSet);
        }

        return messages;
    }

    public static Optional<Message> mapToMessage(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(
                    new Message(
                            UUID.fromString(resultSet.getString(2)),
                            resultSet.getTimestamp(1).toLocalDateTime(),
                            resultSet.getString(3)
                    ));
        } else {
            return Optional.empty();
        }
    }
}
