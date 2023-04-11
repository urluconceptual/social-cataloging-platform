package ro.project.exceptions;

import java.time.DateTimeException;

public class InvalidDate extends DateTimeException {
    public InvalidDate() {
        super("Invalid date!");
    }
}
