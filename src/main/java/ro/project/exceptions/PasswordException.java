package ro.project.exceptions;

public non-sealed class PasswordException extends UserDataException {
    public PasswordException() {
        super("Wrong password!");
    }
}