package ro.project.mappers;

import ro.project.model.BookClub;
import ro.project.model.PersonalShelf;
import ro.project.model.SharedShelf;
import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;
import ro.project.model.enums.ShelfType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShelfMapper {
    private static ShelfMapper INSTANCE;

    private ShelfMapper() {
    }

    public static ShelfMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShelfMapper();
        }
        return INSTANCE;
    }

    public static Optional<Shelf> mapToShelf(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            if(resultSet.getString(7).equals(ShelfType.PERSONAL.getType())) {
                return Optional.of(
                        PersonalShelf.builder()
                                .id(UUID.fromString(resultSet.getString(1)))
                                     .creationDate(resultSet.getTimestamp(2).toLocalDateTime())
                                     .updateDate(resultSet.getTimestamp(3) == null ? null : resultSet.getTimestamp(3).toLocalDateTime())
                                     .deleteDate(resultSet.getTimestamp(4) == null ? null : resultSet.getTimestamp(4).toLocalDateTime())
                                        .name(resultSet.getString(6))
                                        .type(ShelfType.getEnumByFieldString(resultSet.getString(7)))
                                     .build()
                                  );
            }
            else {
                return Optional.of(
                        SharedShelf.builder()
                                   .id(UUID.fromString(resultSet.getString(1)))
                                   .creationDate(resultSet.getTimestamp(2).toLocalDateTime())
                                   .updateDate(resultSet.getTimestamp(3) == null ? null : resultSet.getTimestamp(3).toLocalDateTime())
                                   .deleteDate(resultSet.getTimestamp(4) == null ? null : resultSet.getTimestamp(4).toLocalDateTime())
                                   .name(resultSet.getString(6))
                                   .type(ShelfType.getEnumByFieldString(resultSet.getString(7)))
                                   .build()
                                  );
            }
        } else {
            return Optional.empty();
        }
    }

    public static List<Shelf> mapToShelfList(ResultSet resultSet) throws SQLException {
        List<Shelf> shelves = new ArrayList<>();
        Optional<Shelf> shelf = mapToShelf(resultSet);
        while (shelf.isPresent()) {
            shelves.add(shelf.get());
            shelf = mapToShelf(resultSet);
        }

        return shelves;
    }
}
