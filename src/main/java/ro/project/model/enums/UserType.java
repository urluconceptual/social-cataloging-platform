package ro.project.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum UserType {
    AUTHOR("author"),
    LIBRARIAN("librarian"),
    READER("reader"),
    NONE("none");

    private final String type;

    public static UserType getEnumByFieldString(String field) {
        return Arrays.stream(UserType.values())
                     .filter(enumElement -> enumElement.type.equals(field))
                     .findAny()
                     .orElse(NONE);
    }
}
