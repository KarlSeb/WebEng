package qasystem.web.dtos;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * DTO zum Kapseln der Daten eines Benutzers.
 */
public class UserDTO {
    @NotNull
    @NotEmpty
    private String userName;
    @NotNull
    @NotEmpty
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
