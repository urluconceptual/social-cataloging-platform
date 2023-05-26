package ro.project.mappers;

import ro.project.model.Connection;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ConnectionMapper {
    private static ConnectionMapper INSTANCE;

    private ConnectionMapper() {
    }

    public static ConnectionMapper getInstance() {
        return (INSTANCE == null ? new ConnectionMapper() : INSTANCE);
    }

    public static Optional<Connection> mapToConnection(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(
                    Connection.builder()
                                    .id(UUID.fromString(resultSet.getString(1)))
                                    .creationDate(resultSet.getTimestamp(2).toLocalDateTime())
                                    .updateDate(resultSet.getTimestamp(3) == null ? null : resultSet.getTimestamp(3).toLocalDateTime())
                                    .deleteDate(resultSet.getTimestamp(4) == null ? null : resultSet.getTimestamp(4).toLocalDateTime())
                                    .follower(UUID.fromString(resultSet.getString(6)))
                                    .followed(UUID.fromString(resultSet.getString(7)))
                                    .build()
                                  );
        } else {
            return Optional.empty();
        }
    }

    public static List<Connection> mapToConnectionList(ResultSet resultSet) throws SQLException {
        List<Connection> connections = new ArrayList<>();
        Optional<Connection> connection = mapToConnection(resultSet);
        while (connection.isPresent()) {
            connections.add(connection.get());
            connection = mapToConnection(resultSet);
        }

        return connections;
    }
}