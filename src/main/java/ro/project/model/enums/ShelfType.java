package ro.project.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter

public enum ShelfType {
    PERSONAL("personal"),
    SHARED("shared");

    public final String type;

    public static ShelfType getEnumByFieldString(String field) {
        return Arrays.stream(ShelfType.values())
                     .filter(enumElement -> enumElement.type.equals(field))
                     .findAny()
                     .orElse(null);
    }
}
