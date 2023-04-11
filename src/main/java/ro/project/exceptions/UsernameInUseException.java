package ro.project.exceptions;

public final class UsernameInUseException extends UsernameException {
    public UsernameInUseException() {
        super("Username already in use!");
    }
}