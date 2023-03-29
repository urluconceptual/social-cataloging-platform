package ro.project.model.abstracts;

import ro.project.model.enums.UserType;

import lombok.experimental.SuperBuilder;
import lombok.Getter;

@SuperBuilder
@Getter
public abstract class User extends AbstractEntity {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private UserType type;
}
