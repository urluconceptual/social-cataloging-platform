package ro.project.model.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter

public enum EditionFormat {
    PHYSICAL("physical"),
    EBOOK("ebook"),
    AUDIOBOOK("audiobook"),
    OTHER("other");

    private String format;

    public EditionFormat getEnumByFieldString(String format) {
        return Arrays.stream(EditionFormat.values())
                     .filter(enumElement -> enumElement.format.equals(format))
                     .findAny()
                     .orElse(OTHER);
    }
}
