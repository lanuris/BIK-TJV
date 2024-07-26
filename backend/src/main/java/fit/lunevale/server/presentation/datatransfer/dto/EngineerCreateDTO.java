package fit.lunevale.server.presentation.datatransfer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EngineerCreateDTO {
    @NotNull(message = "Input the name!")
    @Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
    private String name;
    @NotNull(message = "Input the Surname!")
    @Size(min = 1, max = 30, message = "Surname must be between 1 and 30 characters")
    private String surname;
    @Email
    private String email;

    @NotNull(message = "Input the password!")
    @Size(min = 4, max = 30, message = "Password must be between 4 and 30 characters")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
