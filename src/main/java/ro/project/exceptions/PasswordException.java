package ro.project.exceptions;

public class PasswordException extends RuntimeException{
    public PasswordException() {
        super("Wrong password!");
    }
}