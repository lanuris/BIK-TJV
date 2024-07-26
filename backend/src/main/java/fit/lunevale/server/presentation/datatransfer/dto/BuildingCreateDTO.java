package fit.lunevale.server.presentation.datatransfer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BuildingCreateDTO {

    @NotNull(message = "Input the name!")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;
    @NotNull(message = "Input building type!")
    @Size(min = 3, max = 30, message = "Building type must be between 3 and 30 characters")
    private String building_type;
    @NotNull(message = "Project ID must be provided")
    private Long inProjectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding_type() {
        return building_type;
    }

    public void setBuilding_type(String building_type) {
        this.building_type = building_type;
    }

    public Long getInProjectId() {
        return inProjectId;
    }

    public void setInProjectId(Long inProjectId) {
        this.inProjectId = inProjectId;
    }
}
