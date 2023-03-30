package ro.project.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum GenreType {
    MYSTERY("Mystery"),
    SCIENCE_FICTION("Science Fiction"),
    ROMANCE("Romance"),
    HORROR("Horror"),
    BIOGRAPHY("Biography"),
    NON_FICTION("Non Fiction"),
    OTHER("Other");

    private final String name;

    public static GenreType getEnumByFieldString(String field) {
        return Arrays.stream(GenreType.values())
                     .filter(enumElement -> enumElement.name.equals(field))
                     .findAny()
                     .orElse(OTHER);
    }
}
