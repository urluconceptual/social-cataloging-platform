package ro.project.exceptions;

public class OptionException extends RuntimeException {
    public OptionException() {
        super("Invalid option! Try again!");
    }
}
