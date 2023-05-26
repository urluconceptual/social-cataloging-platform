package ro.project.mappers;

import ro.project.model.*;
import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;
import ro.project.repository.BookClubRepository;
import ro.project.repository.impl.BookClubRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserMapper {
    private static BookClubRepository bookClubRepository = new BookClubRepositoryImpl();
    private static UserMapper INSTANCE;

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        return (INSTANCE == null ? new UserMapper() : INSTANCE);
    }

    public static Optional<User> mapToUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            if (resultSet.getString(12).equals(UserType.READER.getType())) {
                return Optional.of(
                        Reader.builder()
                                .id(UUID.fromString(resultSet.getString(1)))
                                .creationDate(resultSet.getTimestamp(2).toLocalDateTime())
                                .updateDate(resultSet.getTimestamp(3) == null ? null : resultSet.getTimestamp(3).toLocalDateTime())
                                .deleteDate(resultSet.getTimestamp(4) == null ? null : resultSet.getTimestamp(4).toLocalDateTime())
                                .username(resultSet.getString(6))
                                .password(resultSet.getString(7))
                                .firstName(resultSet.getString(8))
                                .lastName(resultSet.getString(9))
                                .birthDate(resultSet.getDate(10).toLocalDate())
                                .bio(resultSet.getString(11))
                                .type(UserType.getEnumByFieldString(resultSet.getString(12)))
                                .averageRating(resultSet.getDouble(14))
                                .build()
                                  );
            }
            else if(resultSet.getString(12).equals(UserType.AUTHOR.getType())) {
                return Optional.of(
                        Author.builder()
                              .id(UUID.fromString(resultSet.getString(1)))
                              .creationDate(resultSet.getTimestamp(2).toLocalDateTime())
                              .updateDate(resultSet.getTimestamp(3) == null ? null : resultSet.getTimestamp(3).toLocalDateTime())
                              .deleteDate(resultSet.getTimestamp(4) == null ? null : resultSet.getTimestamp(4).toLocalDateTime())
                              .username(resultSet.getString(6))
                              .password(resultSet.getString(7))
                              .firstName(resultSet.getString(8))
                              .lastName(resultSet.getString(9))
                              .birthDate(resultSet.getDate(10).toLocalDate())
                              .bio(resultSet.getString(11))
                              .type(UserType.getEnumByFieldString(resultSet.getString(12)))
                              .averageRating(resultSet.getDouble(17))
                              .build()
                                  );
            }
            else {
                BookClub bookClub = bookClubRepository.getById(UUID.fromString(resultSet.getString(19))).get();
                return Optional.of(
                        Librarian.builder()
                                 .id(UUID.fromString(resultSet.getString(1)))
                                 .creationDate(resultSet.getTimestamp(2).toLocalDateTime())
                                 .updateDate(resultSet.getTimestamp(3) == null ? null : resultSet.getTimestamp(3).toLocalDateTime())
                                 .deleteDate(resultSet.getTimestamp(4) == null ? null : resultSet.getTimestamp(4).toLocalDateTime())
                                 .username(resultSet.getString(6))
                                 .password(resultSet.getString(7))
                                 .firstName(resultSet.getString(8))
                                 .lastName(resultSet.getString(9))
                                 .birthDate(resultSet.getDate(10).toLocalDate())
                                 .bio(resultSet.getString(11))
                                 .type(UserType.getEnumByFieldString(resultSet.getString(12)))
                                .bookClub(bookClub)
                                 .build()
                                  );
            }
        } else {
            return Optional.empty();
        }
    }

    public static List<User> mapToUserList(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        Optional<User> user = mapToUser(resultSet);
        while (user.isPresent()) {
            users.add(user.get());
            user = mapToUser(resultSet);
        }

        return users;
    }
}
