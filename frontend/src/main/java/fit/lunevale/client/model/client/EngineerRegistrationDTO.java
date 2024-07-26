package fit.lunevale.client.model.client;

public class EngineerRegistrationDTO extends EngineerCreateDTO {

    private String passwordRepeated;

    public EngineerRegistrationDTO() {}

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    public EngineerCreateDTO convertToCreateDTO (){
        return new EngineerCreateDTO(this.getName(), this.getSurname(), this.getEmail(), this.getPassword());
    }
}
