package ro.project.exceptions;

public sealed class UsernameException extends UserDataException permits UsernameInUseException, UsernameNotRegistered {
    public UsernameException(String message) {
        super(message);
    }
}
