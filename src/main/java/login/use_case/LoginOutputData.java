package login.use_case;

import entity.User;

import java.util.Objects;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final User user;

    public LoginOutputData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginOutputData that = (LoginOutputData) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
