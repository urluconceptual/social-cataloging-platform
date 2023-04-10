package ro.project.exceptions;

public class UsernameInUseException extends UsernameException {
    public UsernameInUseException() {
        super("Username already in use!");
    }
}