package ro.project.mappers;

import ro.project.model.Book;
import ro.project.model.BookClub;
import ro.project.model.Reader;
import ro.project.model.abstracts.User;
import ro.project.model.enums.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookClubMapper {
    private static BookClubMapper INSTANCE;

    private BookClubMapper() {
    }

    public static BookClubMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookClubMapper();
        }
        return INSTANCE;
    }

    public static Optional<BookClub> mapToBookClub(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
                return Optional.of(
                        BookClub.builder()
                                .id(UUID.fromString(resultSet.getString(1)))
                                .creationDate(resultSet.getTimestamp(2).toLocalDateTime())
                                .updateDate(resultSet.getTimestamp(3) == null ? null : resultSet.getTimestamp(3).toLocalDateTime())
                                .deleteDate(resultSet.getTimestamp(4) == null ? null : resultSet.getTimestamp(4).toLocalDateTime())
                                .build()
                                  );
        } else {
            return Optional.empty();
        }
    }

    public static List<BookClub> mapToBookClubList(ResultSet resultSet) throws SQLException {
        List<BookClub> bookClubs = new ArrayList<>();
        Optional<BookClub> bookClub = mapToBookClub(resultSet);
        while (bookClub.isPresent()) {
            bookClubs.add(bookClub.get());
            bookClub = mapToBookClub(resultSet);
        }

        return bookClubs;
    }
}
