package fit.lunevale.server.presentation.datatransfer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProjectCreateDTO {

    @NotNull(message = "Input the name!")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;
    @NotNull(message = "Input type!")
    @Size(min = 3, max = 30, message = "Type must be between 3 and 30 characters")
    private String type;
    @NotNull(message = "Input price!")
    @Positive(message = "Price must be positive number.")
    private Long price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
