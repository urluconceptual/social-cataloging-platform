package ro.project.exceptions;

public sealed class UserDataException extends RuntimeException permits UsernameException, PasswordException {
    public UserDataException(String message) {
        super(message);
    }
}
