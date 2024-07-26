package fit.lunevale.client.model.client;

public class EngineerLoginDTO {

    private String email;
    private String password;

    public EngineerLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public EngineerLoginDTO() {
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
