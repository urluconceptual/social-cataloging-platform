package ro.project.mappers;

import ro.project.model.Reader;
import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserMapper {
    private static UserMapper INSTANCE;

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        return (INSTANCE == null ? new UserMapper() : INSTANCE);
    }

    public static Optional<User> mapToUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            if (resultSet.getString(8).equals(UserType.READER.getType())) {
                return Optional.of(
                        Reader.builder()
                                .id(UUID.fromString(resultSet.getString(1)))
                                .creationDate(resultSet.getTimestamp(2).toLocalDateTime())
                                .updateDate(resultSet.getTimestamp(3).toLocalDateTime())
                                .deleteDate(resultSet.getTimestamp(4).toLocalDateTime())
                                .username(resultSet.getString(5))
                                .password(resultSet.getString(6))
                                .firstName(resultSet.getString(7))
                                .lastName(resultSet.getString(8))
                                .birthDate(resultSet.getDate(9).toLocalDate())
                                .bio(resultSet.getString(10))
                                .type(UserType.valueOf(resultSet.getString(11)))
                                .averageRating(resultSet.getDouble(12))
                                .build()
                                  );
            }
            else if(resultSet.getString(8).equals(UserType.AUTHOR.getType())) {
                return Optional.of(
                        Reader.builder()
                              .id(UUID.fromString(resultSet.getString(1)))
                              .creationDate(resultSet.getTimestamp(2).toLocalDateTime())
                              .updateDate(resultSet.getTimestamp(3).toLocalDateTime())
                              .deleteDate(resultSet.getTimestamp(4).toLocalDateTime())
                              .username(resultSet.getString(5))
                              .password(resultSet.getString(6))
                              .firstName(resultSet.getString(7))
                              .lastName(resultSet.getString(8))
                              .birthDate(resultSet.getDate(9).toLocalDate())
                              .bio(resultSet.getString(10))
                              .type(UserType.valueOf(resultSet.getString(11)))
                              .averageRating(resultSet.getDouble(12))
                              .build()
                                  );
            }
            else {
                return Optional.of(
                        Reader.builder()
                              .id(UUID.fromString(resultSet.getString(1)))
                              .creationDate(resultSet.getTimestamp(2).toLocalDateTime())
                              .updateDate(resultSet.getTimestamp(3).toLocalDateTime())
                              .deleteDate(resultSet.getTimestamp(4).toLocalDateTime())
                              .username(resultSet.getString(5))
                              .password(resultSet.getString(6))
                              .firstName(resultSet.getString(7))
                              .lastName(resultSet.getString(8))
                              .birthDate(resultSet.getDate(9).toLocalDate())
                              .bio(resultSet.getString(10))
                              .type(UserType.valueOf(resultSet.getString(11)))
                              .build()
                                  );
            }
        } else {
            return Optional.empty();
        }
    }

    public static List<User> mapToUserList(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while(resultSet.next()) {
            resultSet.previous();
            users.add(mapToUser(resultSet).get());
        }
        return users;
    }
}
