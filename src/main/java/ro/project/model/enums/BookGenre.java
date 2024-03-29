package ro.project.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum BookGenre {
    MYSTERY("Mystery"),
    SCIENCE_FICTION("Science Fiction"),
    ROMANCE("Romance"),
    HORROR("Horror"),
    BIOGRAPHY("Biography"),
    NON_FICTION("Non Fiction"),
    OTHER("Other");

    public final String name;

    public static BookGenre getEnumByFieldString(String field) {
        return Arrays.stream(BookGenre.values())
                     .filter(enumElement -> enumElement.name.equals(field))
                     .findAny()
                     .orElse(OTHER);
    }
}
